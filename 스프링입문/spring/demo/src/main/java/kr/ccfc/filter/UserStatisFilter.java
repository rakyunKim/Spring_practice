package kr.ccfc.filter;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import kr.ccfc.base.BaseUtil;
import kr.ccfc.domain.SysUserStatis;
import kr.ccfc.service.SysUserStatisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class UserStatisFilter implements Filter, Ordered {

    // 不需要token
    @Value("${statis.urls:/visit}")
    private Set<String> statisUrls;

    @Autowired
    private SysUserStatisService sysUserStatisService;

    /**
     * 过滤器顺序
     */
    @Override
    public int getOrder() {
        return 10;
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
        // 统计主页访问
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();
        if (statisUrls.contains(requestURI)) {
            try {
                String agent = request.getHeader("user-agent");
                UserAgent ua = UserAgentUtil.parse(agent);
                String browser = ua.getBrowser().toString();//Chrome
                String version = ua.getVersion();//14.0.835.163
                String engine = ua.getEngine().toString();//Webkit
                String engineVersion = ua.getEngineVersion();//535.1
                String os = ua.getOs().toString();//Windows 7
                String platForm = ua.getPlatform().toString();//Windows
                boolean isMobile = ua.isMobile();
                // 访客ip地址
                String remoteHost = request.getRemoteHost();
                Long userId = BaseUtil.getUserId();
                // 根据ip查找记录是否存在
                SysUserStatis sysUserStatis = new SysUserStatis();
                sysUserStatis.setBrowser(browser);
                sysUserStatis.setDevice(isMobile ? "MOBILE" : "PC");
                sysUserStatis.setEngine(engine);
                sysUserStatis.setEngineVersion(engineVersion);
                sysUserStatis.setOs(os);
                sysUserStatis.setPlatForm(platForm);
                sysUserStatis.setRemoteHost(remoteHost);
                sysUserStatis.setUrl(requestURI);
                sysUserStatis.setUserId(userId);
                sysUserStatis.setVersion(version);
                sysUserStatisService.save(sysUserStatis);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
