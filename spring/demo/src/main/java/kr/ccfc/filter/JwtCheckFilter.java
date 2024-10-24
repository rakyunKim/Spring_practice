package kr.ccfc.filter;

import com.alibaba.fastjson.JSONObject;
import com.google.common.net.HttpHeaders;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * 访问网关的时候，校验jwt是否失效
 * 通过读redis判断是否包含该key判断
 */
@Component
public class JwtCheckFilter implements Filter, Ordered {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 不需要token
    @Value("${no.require.urls:/user/login,/,/register,/login,/oauth/token,/favicon.ico,/user/logout,/oauth/token")
    private Set<String> noRequireTokenUris;


    /**
     * 给用户响应没有token的数据
     */
    private void buildNoAuthorizationResult(HttpServletResponse response) throws IOException {
        response.setHeader("Content-Type", "application/json");
        response.setStatus(401);
        JSONObject object = new JSONObject();
        object.put("error", "auth faild");
        object.put("msg", "Token is Error");
        byte[] bytes = object.toJSONString().getBytes(StandardCharsets.UTF_8);
        response.getWriter().write(new String(bytes));
    }

    private boolean isRequiredToken(HttpServletRequest request) {
        String path = request.getRequestURI();
        // 静态资源全部放行
        if (path.startsWith("/")) {
            return false;
        } else {
            return !noRequireTokenUris.contains(path);
        }
    }

    /**
     * 从请求头获取token
     */
    private String getUserToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        return StringUtils.isEmpty(token) ? "" : token.replaceAll("bearer ", "");
    }

    /**
     * 拦截器顺序
     */
    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    /**
     * 拦截到用户请求后，校验jwt状态
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 接口是否需要token才能访问
        if (!isRequiredToken(request)) {
            // 不需要token 直接放行
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        // 取出用户token，判断token是否有效
        String token = getUserToken(request);
        if (StringUtils.isEmpty(token)) {
            buildNoAuthorizationResult(response);
            return;
        }
        Boolean has = stringRedisTemplate.hasKey(token);
        // token有效
        if (BooleanUtils.isTrue(has)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        buildNoAuthorizationResult(response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
