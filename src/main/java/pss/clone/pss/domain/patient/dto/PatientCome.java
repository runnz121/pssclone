package pss.clone.pss.domain.patient.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access= AccessLevel.PROTECTED)
public class PatientCome {
    /**
     * 환자 차트번호
     */
    private String patChtNum;

    /**
     * 이름
     */
    private String patNam;

    /**
     * 내원번호
     */
    private int comNum;

    /**
     * 환자 유형
     */
    private String comPatTyp;

    /**
     * 진료과 코드
     */
    private String depCod;

    /**
     * 진료과 한글명
     */
    private String depKorNam;

    /**
     * 병실 코드
     */
    private String cowRomCod;
}