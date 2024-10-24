package kr.ccfc.service.impl;

import kr.ccfc.constants.LoginConstant;
import kr.ccfc.domain.SysPrivilege;
import kr.ccfc.domain.SysRole;
import kr.ccfc.service.SysPrivilegeService;
import kr.ccfc.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 登录逻辑处理
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysPrivilegeService sysPrivilegeService;

    /**
     * 登录入口
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        UserDetails user;
        try {
            String grantType =
                    requestAttributes.getRequest()
                            .getParameter("grant_type"); // refresh token 使用的是 id作为username,使用refresh_token是要进行纠正
            if (!StringUtils.isEmpty(grantType) && LoginConstant.REFRESH_TYPE.equals(grantType.toUpperCase(Locale.ROOT))) {
                username = adjustUserName(username);
            }
            user = loadSysUserName(username);
        } catch (IncorrectResultSizeDataAccessException e) {
            log.error(e.getMessage(), e);
            throw new UsernameNotFoundException("사용자" + username + "없음");
        }
        return user;
    }

    private UserDetails loadSysUserName(String username) {
        // 使用用户名查询用户
        return jdbcTemplate.queryForObject(LoginConstant.QUERY_ADMIN_SQL, (rs, i) -> {
            // 查询用户对应的权限
            if (rs.wasNull()) {
                throw new UsernameNotFoundException("사용자" + username + "없음");
            }
            long id = rs.getLong("id");
            String password = rs.getString("password");
            int status = rs.getInt("status");
            // 设置用户名，密码，状态，账号是否过期，密码是否过期，账号是否被锁定，
            // 使用userId 代替username
            return new User(id + "", password, status == 1, true, true, true
                    , getSysUserPermission(id)); // 封装用户对象
        }, username);
    }

    /**
     * 纠正username
     */
    private String adjustUserName(String username) {
        return jdbcTemplate.queryForObject(LoginConstant.QUERY_ADMIN_USER_WITH_ID, String.class, username);
    }

    /**
     * 根据id 查询用户权限数据
     */
    private Collection<? extends GrantedAuthority> getSysUserPermission(Long id) {
        List<String> permissions;
        // 当用户为超级管理员时，拥有所有的权限数据
        List<String> roleCode = jdbcTemplate.queryForList(LoginConstant.QUERY_ROLE_CODE_SQL, String.class, id);
        if (roleCode.contains(LoginConstant.ADMIN_ROLE_CODE)) { //超级管理员用户
            permissions = jdbcTemplate.queryForList(LoginConstant.QUERY_ALL_PERMISSIONS, String.class);
        } else {
            // 先拿到用户的角色数据
            List<SysRole> userRoles = sysRoleService.findByUserId(id);
            // 获取级别最高的角色，拿到角色包含的权限
            SysRole sysRole = userRoles.stream().min(Comparator.comparingInt(SysRole::getRanks)).get();
            // 查询角色所有的权限 根据权限等级
            List<SysPrivilege> privileges = sysPrivilegeService.queryByRoleRanks(sysRole.getRanks());
            permissions = privileges.stream().map(SysPrivilege::getName).collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty(permissions)) {
            return Collections.emptySet();
        } else {
            return permissions.stream()
                    .distinct()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
    }
}
