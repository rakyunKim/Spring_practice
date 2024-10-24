package kr.ccfc.base;

import kr.ccfc.domain.SysUser;
import kr.ccfc.service.SysUserService;
import kr.ccfc.service.impl.SysUserServiceImpl;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseUtil {


    public static Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 从安全的上下文里面获取用户的ud
        if (authentication != null) {
            String userIdString = authentication.getPrincipal().toString(); // userId -> Long  anonymousUser
            if ("anonymousUser".equals(userIdString)) { // 内部服务调用
                return 0L;
            }
            return Long.valueOf(userIdString);
        }
        return 0L;
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }

    public static HttpServletResponse getResponse() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getResponse() : null;
    }
}
