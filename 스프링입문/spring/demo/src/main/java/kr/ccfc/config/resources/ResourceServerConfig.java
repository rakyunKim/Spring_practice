package kr.ccfc.config.resources;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

import java.nio.charset.StandardCharsets;

@EnableResourceServer //资源服务
@EnableGlobalMethodSecurity(prePostEnabled = true) //基于方法注解的权限认证
@Configuration
@Slf4j
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private OAuth2WebSecurityExpressionHandler expressionHandler;

    /**
     * 拦截放行某些路径
     * 需要从鉴权服务器获取token才能够访问资源
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().disable()
                .authorizeRequests()

                .antMatchers(
                        "/", "/front/**", "/portal/**", "/oauth/**", "/imgs/**", "/admin", "/user/logout", "/user/login", "/oauth/**",
                        "/images/**", "/competition/calendar",
                        "/pop", "/syspop/getById", "/syspop/showPop", "/syspop/visitorstatus", "/menu/nologinMenus", "/visit",
                        "/login", "/memberapply",
                        "/register",
                        "/registerform", "/sub*", "/competition/list", "/user/sendResetEmail", "/user/sendIdFindEmail",
                        "/static/**",
                        "/js/**", "/css/**", "/favicon.ico", "/user/resetConfirm",
                        "/img/**",
                        "/webjars/**", "/ranking/list", "/user/idCheck/**", "/competition/getExcel",
                        "/competition/downloadExcel", "/ranking/downloadExcel", "/memberapply/downloadExcel",
                        "/sso/**", "/ins/**", "/ins/media"
                ).permitAll() //放行
//                .anyRequest().access("@authService.canAccess(request,authentication)")
                .antMatchers("/**").authenticated() // 动态鉴权先不做了
                .and().headers().cacheControl();
    }

    /**
     * 设置公钥
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenStore(jwtTokenStore());
        resources.expressionHandler(expressionHandler);
    }

    public JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(accessTokenConverter());

    }

    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }

    @Bean //放在ioc中
    public JwtAccessTokenConverter accessTokenConverter() {
        // resource 验证 token(公钥), authorization 产生 token(私钥)
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        String publicKey = null;
        try {
            ClassPathResource classPathResource = new ClassPathResource("publicKey.txt");
            byte[] bytes = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
            publicKey = new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        jwtAccessTokenConverter.setVerifierKey(publicKey);
        return jwtAccessTokenConverter;
    }
}
