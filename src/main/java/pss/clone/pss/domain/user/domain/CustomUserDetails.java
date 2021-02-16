package pss.clone.pss.domain.user.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomUserDetails implements UserDetails{
    private static final long serialVersionUID = 1L;

    private String 	username;
    private String	password;
    private String 	name;
    private String 	depCod;
    private boolean	accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;


    public CustomUserDetails(String username, String password, String name, String depCod, String hospital, boolean accountNonExpired, boolean accountNonLocked,
                             boolean credentialsNonExpired, boolean enabled) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.depCod = depCod;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    public CustomUserDetails(User user) {
        this.username = user.getUidCod();
        this.password = user.getUidPwd();
        this.name = user.getUidNam();
        this.depCod = user.getUidDepCod();
        this.accountNonExpired = user.isAccountNonExpired();
        this.accountNonLocked = user.isAccountNonLocked();
        this.credentialsNonExpired = user.isCredentialsNonExpired();
        this.enabled = user.isEnabled();
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auth = new ArrayList<>();
        String role = ("MSYS".equals(this.username)) ? "ROLE_MSYS" : "ROLE_USER";
        auth.add(new SimpleGrantedAuthority(role));
        return auth;
    }
}