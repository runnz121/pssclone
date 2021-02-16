package pss.clone.pss.domain.hospital.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Ward {

    private String wadCod;

    private String wadKorNam;

    public Ward(String wadCod, String wadKorNam) {
        this.wadCod = wadCod;
        this.wadKorNam = wadKorNam;
    }
}
