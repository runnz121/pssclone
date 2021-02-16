package pss.clone.pss.domain.model;

public enum WorkID {
    NONE("MN000"),
    PatientChartCheck("MN001"),
    SpecimenBarcodeCheck("MN002"),
    SpecimenOrder("MN003"),
    Order("MN004"),
    Progress("MN005"),
    Labs("MN006"),
    LabResutls("MN007"),
    ConsentPatientInfo("MN008"),
    ConsentSave("MN009"),
    ActingSave("MN010"),
    ActingDelete("MN011"),
    BSTUpdate("MN012");

    private String code;

    WorkID(String code) {
        this.code = code;
    }

    public String get() {
        return code;
    }
}
