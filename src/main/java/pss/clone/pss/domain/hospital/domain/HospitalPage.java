package pss.clone.pss.domain.hospital.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class HospitalPage {

    private String	hospitalId;
    private boolean patientSafety;
    private boolean wardPatient;
    private boolean consent;
    private boolean prescription;
    private boolean blockchain;

    public HospitalPage(String hospitalId, boolean patientSafety, boolean wardPatient, boolean consent,
                        boolean prescription, boolean blockchain) {
        this.hospitalId = hospitalId;
        this.patientSafety = patientSafety;
        this.wardPatient = wardPatient;
        this.consent = consent;
        this.prescription = prescription;
        this.blockchain = blockchain;
    }
}