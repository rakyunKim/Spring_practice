package kr.ccfc.controller.sso;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import kr.ccfc.domain.SysUser;
import kr.ccfc.model.JwtToken;
import kr.ccfc.model.LoginResult;
import kr.ccfc.model.R;
import kr.ccfc.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@RestController
@Slf4j
@RequestMapping("/sso")
public class SsoLoginController {

    private String KA_KAO_OAUTH_URL = "https://kauth.kakao.com/oauth/token";

    private String KA_KAO_USER_URL = "https://kapi.kakao.com/v2/user/me";

    private String SCOPES = "https://kapi.kakao.com/v2/user/revoke/scopes";

    private String NAVER_OAUTH_URL = "https://nid.naver.com/oauth2.0/token";

    private String NAVER_USER_URL = "https://openapi.naver.com/v1/nid/me";

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/kaKaoLogin")
    public R<LoginResult> kaKaoLogin(@RequestBody JSONObject jsonObject) {
        String body = HttpRequest.post(KA_KAO_OAUTH_URL)
                .contentType(ContentType.FORM_URLENCODED.getValue())
                .charset(StandardCharsets.UTF_8)
                .form("grant_type", "authorization_code")
                .form("redirect_uri", jsonObject.getString("redirect_uri"))
                .form("client_id", jsonObject.getString("client_id"))
                .form("code", jsonObject.getString("code"))
                .execute().body();
        JwtToken jwtToken = JSONObject.parseObject(body, JwtToken.class);
        log.info("허가token：{}", JSON.toJSONString(jwtToken.getAccessToken(), true));

        String encodeKaKaoUrl = UrlBuilder.of(KA_KAO_USER_URL)
                .addQuery("property_keys", "[\"kakao_account.email\"]")
                .build();

        // 获取用户信息
        String userInfo = HttpRequest
                .post(encodeKaKaoUrl)
                .auth(jwtToken.getTokenType() + " " + jwtToken.getAccessToken())
                .form("target_id_type", "user_id")
                .execute().body();

        log.info(userInfo);
        JSONObject userInfoJson = JSONObject.parseObject(userInfo);
        Long kaKaoId = userInfoJson.getLong("id");

        // 判断是否需要注册账号
        SysUser kaKaoUser = sysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getKaKaoId, userInfoJson.getString("id"))
        );
        if (kaKaoUser == null) {
            String pwd = RandomUtil.randomString(5);
            kaKaoUser = SysUser.builder()
                    .username(RandomUtil.randomString(5))
                    .password(pwd)
                    .origPwd(pwd)
                    .mobile(RandomUtil.randomString(11))
                    .email(RandomUtil.randomString(11))
                    .nickName("kakao:" + kaKaoId)
                    .kaKaoId(kaKaoId)
                    .lastVisitTime(new Date())
                    .build();
            sysUserService.register(kaKaoUser);
        }

        return R.ok(sysUserService.login(kaKaoUser.getUsername(), kaKaoUser.getOrigPwd()));
    }

    @PostMapping("/fbLogin")
    public R<Object> fbLogin(@RequestBody JSONObject faceParams) {
        System.out.println(faceParams);
        Long userID = faceParams.getLong("userID");
        // 判断是否需要注册账号
        SysUser fbUser = sysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getFbId, userID)
        );
        if (fbUser == null) {
            String pwd = RandomUtil.randomString(5);
            fbUser = SysUser.builder()
                    .username(RandomUtil.randomString(5))
                    .password(pwd)
                    .origPwd(pwd)
                    .mobile(RandomUtil.randomString(11))
                    .email(RandomUtil.randomString(11))
                    .nickName("facebook:" + userID)
                    .kaKaoId(userID)
                    .lastVisitTime(new Date())
                    .build();
            sysUserService.register(fbUser);
        }
        return R.ok(sysUserService.login(fbUser.getUsername(), fbUser.getOrigPwd()));
    }


    @PostMapping("/naverLogin")
    public R<LoginResult> naverLogin(@RequestBody JSONObject jsonObject) {
        String body = HttpRequest.post(NAVER_OAUTH_URL)
                .contentType(ContentType.FORM_URLENCODED.getValue())
                .charset(StandardCharsets.UTF_8)
                .form("grant_type", "authorization_code")
                .form("redirect_uri", jsonObject.getString("redirect_uri"))
                .form("client_id", jsonObject.getString("client_id"))
                .form("client_secret", jsonObject.getString("client_secret"))
                .form("code", jsonObject.getString("code"))
                .execute().body();
        JwtToken jwtToken = JSONObject.parseObject(body, JwtToken.class);
        log.info("허가token：{}", JSON.toJSONString(jwtToken.getAccessToken(), true));

        // 获取用户信息
        String userInfo = HttpRequest
                .get(NAVER_USER_URL)
                .auth(jwtToken.getTokenType() + " " + jwtToken.getAccessToken())
                .execute().body();
        log.info(userInfo);
        JSONObject userInfoJson = JSONObject.parseObject(userInfo);
        JSONObject response = userInfoJson.getJSONObject("response");
        String naverId = response.getString("id");
        String nickname = response.getString("name");
        String birthyear = response.getString("birthyear");
        String mobile = response.getString("mobile");
        String email = response.getString("email");
        String mobileE164 = response.getString("mobile_e164");

        // 判断是否需要注册账号
        SysUser naverUser = sysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getNaverId, naverId)
        );
        if (naverUser == null) {
            String pwd = RandomUtil.randomString(5);
            naverUser = SysUser.builder()
                    .username(RandomUtil.randomString(5))
                    .password(pwd)
                    .origPwd(pwd)
                    .mobile(mobileE164)
                    .landline(mobile)
                    .email(email)
                    .realName(nickname)
                    .nickName(nickname)
                    .naverId(naverId)
                    .lastVisitTime(new Date())
                    .build();
            sysUserService.register(naverUser);
        }
        return R.ok(sysUserService.login(naverUser.getUsername(), naverUser.getOrigPwd()));
    }


}
