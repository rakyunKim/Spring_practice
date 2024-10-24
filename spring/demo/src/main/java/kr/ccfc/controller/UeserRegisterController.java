package kr.ccfc.controller;

import kr.ccfc.domain.SysUser;
import kr.ccfc.model.R;
import kr.ccfc.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
public class UeserRegisterController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping
    public R<String> register(@RequestBody @Valid SysUser user) {
        user.setType(1);
        try {
            boolean save = sysUserService.register(user);
            // 处理密码
            if (save) {
                return R.ok("가입 성공!");
            } else {
                return R.fail("가입 실패!");
            }
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        }
    }
}
