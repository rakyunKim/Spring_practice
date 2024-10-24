package kr.ccfc.controller;

import com.alibaba.fastjson.JSONObject;
import kr.ccfc.model.LoginForm;
import kr.ccfc.model.LoginResult;
import kr.ccfc.model.R;
import kr.ccfc.service.LoginService;
import kr.ccfc.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserLoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/user/login")
    public R<LoginResult> login(@RequestBody LoginForm loginForm) {
        LoginResult result;
        try {
            result = sysUserService.login(loginForm.getUsername(), loginForm.getPassword());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail("회원 아이디나 비밀번호를 확인하세요");
        }
        return R.ok(result);
    }

    @PostMapping("/user/logout")
    public R<String> logout(@RequestBody JSONObject obj) {
        String token = obj.getString("token");
        if (!StringUtils.isEmpty(token) && token.startsWith("bearer ")) {
            token = token.replaceFirst("bearer ", "");
        }
        boolean flag = loginService.logOut(token);
        return R.ok("로그아웃 성공");
    }

    @GetMapping("/status")
    public R<String> status() {
        return R.ok();
    }
}
