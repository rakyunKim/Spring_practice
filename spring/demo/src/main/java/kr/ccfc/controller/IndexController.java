package kr.ccfc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {
    @GetMapping("/")
    public String init() {
        return "index";
    }

    @GetMapping("/pop")
    public String pop() {
        return "pop";
    }

    @GetMapping("/admin")
    public String initAdmin() {
        return "admin/index";
    }

    @GetMapping("/front/portal/login")
    public String login() {
        return "login";
    }

    @GetMapping("/OauthNaver")
    public String OauthNaver() {
        return "OauthNaver";
    }

    @GetMapping("/OauthKakao")
    public String OauthKakao() {
        return "OauthKakao";
    }

    @GetMapping("/OauthFacebook")
    public String OauthFacebook() {
        return "OauthFacebook";
    }

    @GetMapping("/front/portal/id_find")
    public String id_find() {
        return "id_find";
    }

    @GetMapping("/front/portal/pw_find")
    public String pw_find() {
        return "pw_find";
    }

    @GetMapping("/front/admin/login")
    public String adminLogin() {
        return "admin/admin_login";
    }

    @GetMapping("/front/portal/register")
    public String register() {
        return "sub_register";
    }

    @GetMapping("/front/portal/member_register_form")
    public String member_register_form() {
        return "member_register_form";
    }

    @GetMapping("/front/portal/registerform")
    public String registerForm() {
        return "sub_register_form";
    }

    @GetMapping("/front/portal/privacy")
    public String privacy() {
        return "privacy";
    }

    @GetMapping("/front/portal/terms")
    public String terms() {
        return "terms";
    }

    @GetMapping("/front/portal/emailcollection")
    public String emailcollection() {
        return "emailcollection";
    }

    @GetMapping("/front/portal/sub01_01")
    public String sub01_01() {
        return "sub01_01";
    }

    @GetMapping("/front/portal/sub01_02")
    public String sub01_02() {
        return "sub01_02";
    }

    @GetMapping("/front/portal/sub01_03")
    public String sub01_03() {
        return "sub01_03";
    }

    @GetMapping("/front/portal/sub01_04")
    public String sub01_04() {
        return "sub01_04";
    }

    @GetMapping("/front/portal/sub01_05")
    public String sub01_05() {
        return "sub01_05";
    }

    @GetMapping("/front/portal/sub01_06")
    public String sub01_06() {
        return "sub01_06";
    }

    @GetMapping("/front/portal/sub02_01_list")
    public String sub02_01_list() {
        return "sub02_01_list";
    }

    @GetMapping("/front/portal/sub02_01_view")
    public String sub02_01_view() {
        return "sub02_01_view";
    }

    @GetMapping("/front/portal/sub02_01_write")
    public String sub02_01_write() {
        return "sub02_01_write";
    }

    @GetMapping("/front/portal/sub02_02_write")
    public String sub02_02_write() {
        return "sub02_02_write";
    }

    @GetMapping("/front/portal/sub02_02_list")
    public String sub02_02_list() {
        return "sub02_02_list";
    }

    @GetMapping("/front/portal/sub02_02_view")
    public String sub02_02_view() {
        return "sub02_02_view";
    }

    @GetMapping("/front/portal/sub02_03_list")
    public String sub02_03_list() {
        return "sub02_03_list";
    }

    @GetMapping("/front/portal/sub02_03_view")
    public String sub02_03_view() {
        return "sub02_03_view";
    }

    @GetMapping("/front/portal/sub02_03_write")
    public String sub02_03_write() {
        return "sub02_03_write";
    }

    @GetMapping("/front/portal/sub02_04_list")
    public String sub02_04_list() {
        return "sub02_04_list";
    }

    @GetMapping("/front/portal/sub02_04_view")
    public String sub02_04_view() {
        return "sub02_04_view";
    }

    @GetMapping("/front/portal/sub02_04_write")
    public String sub02_04_write() {
        return "sub02_04_write";
    }

    @GetMapping("/front/portal/sub02_05_list")
    public String sub02_05_list() {
        return "sub02_05_list";
    }

    @GetMapping("/front/portal/sub02_05_view")
    public String sub02_05_view() {
        return "sub02_05_view";
    }

    @GetMapping("/front/portal/sub02_05_write")
    public String sub02_05_write() {
        return "sub02_05_write";
    }

    @GetMapping("/front/portal/sub02_06_list")
    public String sub02_06_list() {
        return "sub02_06_list";
    }

    @GetMapping("/front/portal/sub02_06_view")
    public String sub02_06_view() {
        return "sub02_06_view";
    }

    @GetMapping("/front/portal/sub02_06_write")
    public String sub02_06_write() {
        return "sub02_06_write";
    }

    @GetMapping("/front/portal/sub03_01")
    public String sub03_01() {
        return "sub03_01";
    }

    @GetMapping("/front/portal/sub03_02")
    public String sub03_02() {
        return "sub03_02";
    }

    @GetMapping("/front/portal/sub03_02_01")
    public String sub03_02_01() {
        return "sub03_02_01";
    }

    @GetMapping("/front/portal/sub03_02_02")
    public String sub03_02_02() {
        return "sub03_02_02";
    }

    @GetMapping("/front/portal/sub03_02_03")
    public String sub03_02_03() {
        return "sub03_02_03";
    }

    @GetMapping("/front/portal/sub04_01")
    public String sub04_01() {
        return "sub04_01";
    }

    @GetMapping("/front/portal/sub04_01_01")
    public String sub04_01_01() {
        return "sub04_01_01";
    }

    @GetMapping("/front/portal/sub04_02")
    public String sub04_02() {
        return "sub04_02";
    }

    @GetMapping("/front/portal/sub04_03")
    public String sub04_03() {
        return "sub04_03";
    }

    @GetMapping("/front/portal/sub05_01_list")
    public String sub05_01_list() {
        return "sub05_01_list";
    }

    @GetMapping("/front/portal/sub05_01_view")
    public String sub05_01_view() {
        return "sub05_01_view";
    }

    @GetMapping("/front/portal/sub05_01_write")
    public String sub05_01_write() {
        return "sub05_01_write";
    }

    @GetMapping("/front/portal/sub05_02_list")
    public String sub05_02_list() {
        return "sub05_02_list";
    }

    @GetMapping("/front/portal/sub05_02_view")
    public String sub05_02_view() {
        return "sub05_02_view";
    }

    @GetMapping("/front/portal/sub05_02_write")
    public String sub05_02_write() {
        return "sub05_02_write";
    }

    @GetMapping("/front/portal/sub05_03_list")
    public String sub05_03_list() {
        return "sub05_03_list";
    }

    @GetMapping("/front/portal/sub05_03_view")
    public String sub05_03_view() {
        return "sub05_03_view";
    }

    @GetMapping("/front/portal/sub05_03_write")
    public String sub05_03_write() {
        return "sub05_03_write";
    }

    @GetMapping("/front/portal/sub06_01")
    public String sub06_01() {
        return "sub06_01";
    }

    @GetMapping("/front/portal/sub07_01")
    public String sub07_01() {
        return "sub07_01";
    }

    @GetMapping("/front/portal/sub07_02_list")
    public String sub07_02_list() {
        return "sub07_02_list";
    }

    @GetMapping("/front/portal/sub07_02_view")
    public String sub07_02_view() {
        return "sub07_02_view";
    }

    @GetMapping("/front/portal/sub07_02_write")
    public String sub07_02_write() {
        return "sub07_02_write";
    }

    @GetMapping("/front/portal/sub07_05_list")
    public String sub07_05_list() {
        return "sub07_05_list";
    }

    @GetMapping("/front/portal/sub07_05_view")
    public String sub07_05_view() {
        return "sub07_05_view";
    }

    @GetMapping("/front/portal/sub07_05_write")
    public String sub07_05_write() {
        return "sub07_05_write";
    }

    @GetMapping("/front/portal/sub07_06_list")
    public String sub07_06_list() {
        return "sub07_06_list";
    }

    @GetMapping("/front/portal/sub07_06_view")
    public String sub07_06_view() {
        return "sub07_06_view";
    }

    @GetMapping("/front/portal/sub07_06_write")
    public String sub07_06_write() {
        return "sub07_06_write";
    }

    @GetMapping("/front/portal/sub07_07_list")
    public String sub07_07_list() {
        return "sub07_07_list";
    }

    @GetMapping("/front/portal/sub07_07_view")
    public String sub07_07_view() {
        return "sub07_07_view";
    }

    @GetMapping("/front/portal/sub07_07_write")
    public String sub07_07_write() {
        return "sub07_07_write";
    }

    @GetMapping("/front/portal/sub07_08_list")
    public String sub07_08_list() {
        return "sub07_08_list";
    }

    @GetMapping("/front/portal/sub07_08_view")
    public String sub07_08_view() {
        return "sub07_08_view";
    }

    @GetMapping("/front/portal/sub07_08_write")
    public String sub07_08_write() {
        return "sub07_08_write";
    }

    @GetMapping("/front/portal/sub07_03")
    public String sub07_03() {
        return "sub07_03";
    }

    @GetMapping("/front/admin/menu2_1")
    public String menu2_1() {
        return "admin/menu2_1";
    }

    @GetMapping("/front/admin/menu2_1_1")
    public String menu2_1_1() {
        return "admin/menu2_1_1";
    }

    @GetMapping("/front/admin/index")
    public String adminIndex() {
        return "admin/index";
    }

    @GetMapping("/front/admin/menu1_2")
    public String menu1_2() {
        return "admin/menu1_2";
    }

    @GetMapping("/front/admin/menu1_1_1")
    public String menu1_1_1() {
        return "admin/menu1_1_1";
    }


    @GetMapping("/front/admin/chart")
    public String chart() {
        return "admin/chart";
    }


    @GetMapping("/front/admin/test")
    public String test() {
        return "admin/test";
    }


    @GetMapping("/front/admin/menu1_1")
    public String menu1_1() {
        return "admin/menu1_1";
    }

    @GetMapping("/front/admin/menu3_1")
    public String menu3_1() {
        return "admin/menu3_1";
    }

    @GetMapping("/front/admin/menu3_2")
    public String menu3_2() {
        return "admin/menu3_2";
    }

    @GetMapping("/front/admin/menu4_1")
    public String menu4_1() {
        return "admin/menu4_1";
    }

    @GetMapping("/front/admin/menu4_1_1")
    public String menu4_1_1() {
        return "admin/menu4_1_1";
    }

    @GetMapping("/front/admin/menu5_1")
    public String menu5_1() {
        return "admin/menu5_1";
    }

    @GetMapping("/front/admin/menu5_1_1")
    public String menu5_1_1() {
        return "admin/menu5_1_1";
    }

    @GetMapping("/visit")
    @ResponseBody
    public Map<String, String> visit() {
        return new HashMap<>();
    }

}
