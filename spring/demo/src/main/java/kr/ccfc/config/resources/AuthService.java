package kr.ccfc.config.resources;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import kr.ccfc.domain.SysPrivilege;
import kr.ccfc.service.SysMenuService;
import kr.ccfc.service.SysPrivilegeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.FastArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.spec.DSAPrivateKeySpec;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private SysPrivilegeService sysPrivilegeService;

    public boolean canAccess(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal == null) {
            return false;
        }
        if (authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        // 拿到所有的权限数据
        Set<String> privlieges = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        // 根据uri 拿到权限数据  创建权限的时候就要指定 权限接口的uri 要求规范命名
        String uri = request.getRequestURI();
        if (1 == 1) {
            return true;
        }

        SysPrivilege one = sysPrivilegeService.getOne(new LambdaQueryWrapper<SysPrivilege>()
                .eq(SysPrivilege::getUrl, uri)
        );

        String privilege = one.getName();
        return privlieges.contains(privilege);
    }

}
