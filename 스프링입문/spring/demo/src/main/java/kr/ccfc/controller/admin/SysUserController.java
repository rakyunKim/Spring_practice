package kr.ccfc.controller.admin;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.BooleanUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import kr.ccfc.base.BaseUtil;
import kr.ccfc.confition.SysUserCondition;
import kr.ccfc.domain.SysRole;
import kr.ccfc.domain.SysUser;
import kr.ccfc.domain.SysUserStatis;
import kr.ccfc.model.R;
import kr.ccfc.service.SysUserService;
import kr.ccfc.service.SysUserStatisService;
import kr.ccfc.vo.ChartsVo;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserStatisService sysUserStatisService;

    @GetMapping("/getcuruser")
    public R<SysUser> getCurUser() {
        Long userId = BaseUtil.getUserId();
        SysUser user = sysUserService.getById(userId);
        user.setPassword(null);
        return R.ok(user);
    }


    @GetMapping("/list")
    public R<Page<SysUser>> page(SysUserCondition condition) {
        Date created = condition.getCreated();
        if (created != null) {
            String format = DateUtil.format(created, "yyyy-MM-dd");
            condition.setCreatedStr(format);
        }
        Page<SysUser> userPage = sysUserService.pageQuery(condition);
        for (SysUser record : userPage.getRecords()) {
            record.setPassword(null);
            List<SysRole> roles = record.getRoles();
            if (!CollectionUtils.isEmpty(roles)) {
                Long id = roles.get(0).getId();
                record.setRoleId(id);
            }
        }
        return R.ok(userPage);
    }

    @GetMapping("/getById")
    public R<SysUser> getById(Long userId) {
        SysUser sysUser = sysUserService.getById(userId);
        sysUser.setPassword("");
        List<SysRole> roles = sysUser.getRoles();
        if (!CollectionUtils.isEmpty(roles)) {
            sysUser.setRoleId(roles.get(0).getId());
        }
        return R.ok(sysUser);
    }

    @GetMapping("/idCheck/{username}")
    public R<String> idCheck(@PathVariable String username) {
        List<SysUser> list = sysUserService.list(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username)
        );
        if (CollectionUtils.isEmpty(list)) {
            return R.ok("사용가능한 ID입니다.");
        } else {
            return R.ok("ID 사용중입니다");
        }
    }

    @PostMapping("/save")
    public R<String> saveSysUser(@RequestBody SysUser sysUser) {
        try {
            boolean success = sysUserService.saveSysUser(sysUser);
            return R.ok();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }

    }

    @GetMapping("/statis")
    public R<ChartsVo> initCharts(@DateTimeFormat(pattern = "yyyy-MM-dd")
                                          Date start, @DateTimeFormat(pattern = "yyyy-MM-dd")
                                          Date end) {
        ChartsVo chartsVo = sysUserService.statisToCharts(start, end);
        return R.ok(chartsVo);
    }

    /**
     * 获取用户访问记录
     */
    @GetMapping("/getUserOperateRecords")
    public R<Page<SysUserStatis>> getUserOperateRecords(Page<SysUserStatis> page) {
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<SysUserStatis> res = sysUserStatisService.page(page, new LambdaQueryWrapper<SysUserStatis>()
                .ne(SysUserStatis::getUserId, 0L)
        );
        List<SysUserStatis> records = res.getRecords();
        List<Long> userIds = records.stream().map(SysUserStatis::getUserId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(userIds)) {
            List<SysUser> list = sysUserService.list(
                    new LambdaQueryWrapper<SysUser>()
                            .in(SysUser::getId, userIds)
            );
            Map<Long, SysUser> idNameMap = list.stream().collect(Collectors.toMap(SysUser::getId, e -> e));
            records.forEach(e -> {
                SysUser sysUser = idNameMap.get(e.getUserId());
                if (sysUser != null) {
                    e.setUsername(sysUser.getUsername());
                    e.setRealname(sysUser.getRealName());
                }
            });
        }

        return R.ok(page);
    }

    @PostMapping("/sendResetEmail")
    public R<String> sendResetEmail(@RequestBody JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String email = jsonObject.getString("email");
        Boolean aBoolean = sysUserService.sendResetEmail(username, email);
        if (BooleanUtil.isTrue(aBoolean)) {
            return R.ok();
        }
        return R.fail("발송 실패，아이디나 이메일 확인 하세요");
    }

    @PostMapping("/resetConfirm")
    public R<String> resetConfirm(@RequestBody JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String email = jsonObject.getString("email");
        String code = jsonObject.getString("code");
        String newPwd = jsonObject.getString("newPwd");
        boolean success = sysUserService.resetConfirm(username, email, code, newPwd);
        if (success) {
            return R.ok("리셋 성공");
        } else {
            return R.fail("인증코드 오류");
        }
    }

    @PostMapping("/sendIdFindEmail")
    public R<String> sendIdFindEmail(@RequestBody JSONObject jsonObject) {
        String realName = jsonObject.getString("realName");
        String email = jsonObject.getString("email");
        boolean success = sysUserService.sendIdFindEmail(realName, email);
        if (success) {
            return R.ok("발송 성공");
        } else {
            return R.fail("발송 실패，이름이나 이메일을 확인하세요");
        }
    }

    @DeleteMapping
    public R<String> deleteById(Long[] ids) {
        for (Long id : ids) {
            boolean res = sysUserService.removeById(id);
        }
        return R.ok("삭제 성공");
    }
}
