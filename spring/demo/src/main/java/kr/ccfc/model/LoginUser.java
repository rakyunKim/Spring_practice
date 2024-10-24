package kr.ccfc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {

    private String username;

    private Long expire;

    private String access_token;

    private String refresh_token;

    List<SimpleGrantedAuthority> authorities;
}
