package kr.ccfc.model;

import kr.ccfc.domain.SysMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResult {

    private String token;

    private List<SysMenu> menus;

    private List<SimpleGrantedAuthority> authorities;

    private Integer ranks;

    private String roleName;

    private String username;

    private Long userId;

}
