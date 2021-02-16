package pss.clone.pss.domain.hospital.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class HospitalMeta {

    private String	bcOrg;
    private	String	bcUserName;
    private String	vpnUrl;
    private	String	vpnPort;
    private	String	hospitalUserId;
    private	double 	lat;
    private	double	lon;
    private	int		radius;

    public HospitalMeta(String bcOrg, String bcUserName, String vpnUrl, String vpnPort, String hospitalUserId,
                        double lat, double lon, int radius) {
        this.bcOrg = bcOrg;
        this.bcUserName = bcUserName;
        this.vpnUrl = vpnUrl;
        this.vpnPort = vpnPort;
        this.hospitalUserId = hospitalUserId;
        this.lat = lat;
        this.lon = lon;
        this.radius = radius;
    }
}
