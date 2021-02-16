package pss.clone.pss.domain.user.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@ToString
public class User {

    private String uidCod;

    private String uidPwd;

    private String uidNam;

    private String uidDepCod;

    private boolean accountNonLocked;

    private boolean enabled;

    private boolean accountNonExpired;

    private boolean credentialsNonExpired;

    private boolean loginUser;

    public User(String uidCod, String uidPwd, String uidNam, String uidDepCod, boolean accountNonLocked,
                boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired) {
        this.uidCod = uidCod;
        this.uidPwd = uidPwd;
        this.uidNam = uidNam;
        this.uidDepCod = uidDepCod;
        this.accountNonLocked = accountNonLocked;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void isLoginUser(String uidCod) {
        this.loginUser = uidCod != null && uidCod.equals(this.uidCod);
    }

}
