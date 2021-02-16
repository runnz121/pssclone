package pss.clone.pss.domain.hospital.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    /**
     * 병동 코드
     */
    private String	romWadCod;

    /**
     * 병실 코드
     */
    private	String	romCod;

    /**
     * 한글이름
     */
    private	String	romKorNam;

    /**
     * 영문이름
     */
    private	String	romEngNam;

    /**
     * 과목 코드
     */
    private	String	romDepCod;


}