package kr.ccfc.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import kr.ccfc.domain.SysMenu;
import kr.ccfc.domain.SysRole;
import kr.ccfc.domain.SysUser;
import kr.ccfc.domain.SysUserRole;
import kr.ccfc.mapper.SysUserMapper;
import kr.ccfc.model.JwtToken;
import kr.ccfc.model.LoginResult;
import kr.ccfc.service.*;
import kr.ccfc.vo.ChartsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Value("${server.port}")
    private Integer port;

    @Value("${basic.token:Basic eXVsdHkwenFvWDZ4RDl4ODp4S3NQdjlyM2ROUlNqTEZq}")
    private String basicToken;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysUserStatisService sysUserStatisService;

    @Autowired
    private MailService mailService;


    @Override
    public SysUser getById(Serializable id) {
        SysUser sysUser = super.getById(id);
        // 查询角色信息
        List<SysRole> roles = sysRoleService.findByUserId(id);
        sysUser.setRoles(roles);
        return sysUser;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean register(SysUser user) {
        String email = user.getEmail();
        String mobile = user.getMobile();
        user.setStatus((byte) 1);
        int count = count(new LambdaQueryWrapper<SysUser>()
                .eq(!StringUtils.isEmpty(email), SysUser::getEmail, email)
                .eq(!StringUtils.isEmpty(mobile), SysUser::getMobile, mobile)
        );
        if (count > 0) {
            throw new IllegalArgumentException("휴대폰번호나 이메일주소가 사용되었습니다!");
        }
        String username = user.getUsername();
        int usernameCount = count(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username)
        );
        if (usernameCount > 0) {
            throw new IllegalArgumentException("ID 사용중입니다");
        }

        String password = user.getPassword();
        String encodePwd = new BCryptPasswordEncoder().encode(password);
        user.setPassword(encodePwd);
        // 默认访客权限
        SysRole visitor = sysRoleService.getOne(new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getRanks, 7)
        );
        SysUserRole sysUserRole = new SysUserRole();
        save(user);
        sysUserRole.setUserId(user.getId());
        sysUserRole.setRoleId(visitor.getId());
        sysUserRoleService.save(sysUserRole);
        return true;
    }

    @Override
    public Page<SysUser> pageQuery(Page<SysUser> page) {
        List<SysUser> sysUserList = sysUserMapper.pageQuery(page);
        page.setRecords(sysUserList);
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysUser(SysUser sysUser) {
        Long userId = sysUser.getId();
        if (userId == null) {
            List<SysUser> list = list(new LambdaQueryWrapper<SysUser>()
                    .eq(SysUser::getUsername, sysUser.getUsername())
            );
            if (!CollectionUtils.isEmpty(list)) {
                throw new IllegalArgumentException("아이디 사용중입니다");
            }
            sysUser.setStatus((byte) 1);
        }
        Long roleId = sysUser.getRoleId();
        String password = sysUser.getPassword();
        if (!StringUtils.isEmpty(password)) {
            password = passwordEncoder.encode(password);
        } else {
            if (userId != null) {
                SysUser user = getById(userId);
                password = user.getPassword();
            }
            // TODO: 2021/7/16 修改密码登出
        }
        sysUser.setPassword(password);
        if (userId == null) {
            sysUserService.save(sysUser);
        } else {
            sysUserService.updateById(sysUser);
        }
        userId = sysUser.getId();
        if (roleId != null) {
            sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRole>()
                    .eq(SysUserRole::getUserId, userId)
            );
            sysUserRoleService.bindUserRole(userId, roleId);
        }
        return true;
    }

    @Override
    public ChartsVo statisToCharts(Date start, Date end) {
        return sysUserStatisService.statisToCharts(start, end);
    }

    @Override
    public LoginResult login(String username, String password) {
        log.info("유저{}로그인", username);
        Map<String, Object> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("username", username);
        params.put("password", password);
        String result = HttpRequest.post("http://localhost:" + port + "/oauth/token")
                .header("Authorization", basicToken)
                .form(params)
                .execute()
                .body();
        JwtToken body = JSONObject.parseObject(result, JwtToken.class);
        log.info("허가token：{}", JSON.toJSONString(body.getAccessToken(), true));
        // 设置token
        String token = body.getAccessToken();
        // 查询菜单数据
        // 解析jwt
        Jwt jwt = JwtHelper.decode(token);
        String claims = jwt.getClaims();
        JSONObject jwtJson = JSON.parseObject(claims);
        Long userId = Long.parseLong(jwtJson.getString("user_name"));
        List<SysMenu> menus = sysMenuService.initMenus(userId);
        // 获取权限数据 (privilege name) -- jwt中已经包含，不需要查询
        JSONArray authoritiesJsonArray = jwtJson.getJSONArray("authorities");
        if (CollectionUtils.isEmpty(authoritiesJsonArray)) {
            authoritiesJsonArray = new JSONArray();
        }
        // 组装权限数据
        List<SimpleGrantedAuthority> authorities = authoritiesJsonArray.stream()
                .map(e -> new SimpleGrantedAuthority(e.toString()))
                .collect(Collectors.toList());

        // token 存入redis ， 配合网关校验token 是否过期
        redisTemplate.opsForValue()
                .set(token, "", body.getExpiresIn(), TimeUnit.SECONDS);

        // 更新用户的最近登录일期
        SysUser sysUser = getById(userId);
        sysUser.setLastVisitTime(new Date());
        sysUserService.updateById(sysUser);
        // 拿到用户的最高角色数据  再根据角色数据拿到所有的权限数据
        List<SysRole> userRoles = sysRoleService.findByUserId(userId);
        // 获取级别最高的角色，拿到角色包含的权限
        int ranks = 10;
        String roleName = "";
        if (!CollectionUtils.isEmpty(userRoles)) {
            SysRole sysRole = userRoles.stream().min(Comparator.comparingInt(SysRole::getRanks)).get();
            ranks = sysRole.getRanks();
            roleName = sysRole.getDescription();
        }

        // 返回token 添加前缀 bearer
        return new LoginResult(body.getTokenType() + " " + token, menus, authorities, ranks, roleName, sysUser.getUsername(), userId);
    }


    @Override
    public Boolean sendResetEmail(String username, String email) {
        SysUser sysUser = getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username)
                .eq(SysUser::getEmail, email)
        );
        try {
            if (sysUser == null) {
                throw new IllegalArgumentException("아이디나 이메일 확인하세요");
            }
            String randomNumbers = RandomUtil.randomNumbers(6);
            mailService.sendHtmlMail(email, "인증 코드 메일", "인증코드" + randomNumbers + "\n" + "인증코드 입력 후 새로운 비밀번호 입력하시고 비밀번호 리셋을 눌러주세요");
            redisTemplate.opsForValue().set("RESET:PASSWORD:" + email, randomNumbers, 10, TimeUnit.MINUTES);
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;

    }

    @Override
    public boolean resetConfirm(String username, String email, String code, String newPwd) {
        String codeRedis = (String) redisTemplate.opsForValue().get("RESET:PASSWORD:" + email);
        if (ObjectUtil.equal(codeRedis, codeRedis)) {
            String encode = passwordEncoder.encode(newPwd);
            SysUser sysUser = getOne(new LambdaQueryWrapper<SysUser>()
                    .eq(SysUser::getUsername, username)
                    .eq(SysUser::getEmail, email)
            );
            sysUser.setPassword(encode);
            updateById(sysUser);
            redisTemplate.delete("RESET:PASSWORD:" + email);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean sendIdFindEmail(String realName, String email) {
        SysUser sysUser = getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getRealName, realName)
                .eq(SysUser::getEmail, email)
        );
        try {
            if (sysUser == null) {
                throw new IllegalArgumentException("이름이나 이메일 확인하세요");
            }
            String username = sysUser.getUsername();
            mailService.sendHtmlMail(email, "춘천시민축구단 관리자입니다.", "회원님의 아이디는:" + username + "입니다.");
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }
}

