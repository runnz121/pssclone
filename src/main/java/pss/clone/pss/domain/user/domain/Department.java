package pss.clone.pss.domain.user.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Department {

    private String	depCod;
    private	String	depKorNam;
    private	String	depEngNam;
    private	String	depOfcNam;
    private String 	depStrDte;
    private String 	depEndDte;
    private String 	depWrkTyp;
    private boolean loginDept;

    public Department(String depCod, String depKorNam, String depEngNam, String depOfcNam, String depStrDte,
                      String depEndDte, String depWrkTyp, boolean loginDept) {
        this.depCod = depCod;
        this.depKorNam = depKorNam;
        this.depEngNam = depEngNam;
        this.depOfcNam = depOfcNam;
        this.depStrDte = depStrDte;
        this.depEndDte = depEndDte;
        this.depWrkTyp = depWrkTyp;
        this.loginDept = loginDept;
    }

    public void isLoginDept(String depCod) {
        this.loginDept = depCod != null && depCod.equals(this.depCod);
    }
}
