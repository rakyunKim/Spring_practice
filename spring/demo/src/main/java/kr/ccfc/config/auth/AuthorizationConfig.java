package kr.ccfc.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@EnableAuthorizationServer //开启授权服务器的功能
@Configuration
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 添加第三方客户端
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("yulty0zqoX6xD9x8") //第三方客户端名称
                .secret(passwordEncoder.encode("xKsPv9r3dNRSjLFj")) //第三方客户端秘钥
                .scopes("all") // 授权范围
                .authorizedGrantTypes("password", "refresh_token") //授权方式
                .accessTokenValiditySeconds(24 * 3600) //token 有效期
                .refreshTokenValiditySeconds(3 * 24 * 3600)// refresh token 有效期
        ;
        super.configure(clients);
    }

    /**
     * 配置验证管理器，UserdetailService
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager) // 登录校验
                .userDetailsService(userDetailsService) // 登录入口
//                .tokenStore(new InMemoryTokenStore());
//                .tokenStore(redisTokenStore()) // 使用redis存储token
                .tokenStore(jwtTokenStore())// 使用jwt作为token
                .tokenEnhancer(jwtAccessTokenConverter()); //解析token

        super.configure(endpoints);
    }

    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 生成、解析token
     */
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();

        // 加载私钥
        ClassPathResource classPathResource = new ClassPathResource("jks/ccfcauth.jks");
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource, "ccfcauth".toCharArray());
        tokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("ccfcauth", "ccfcauth".toCharArray()));
        return tokenConverter;
    }

    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }
}
