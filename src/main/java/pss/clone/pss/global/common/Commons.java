package pss.clone.pss.global.common;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pss.clone.pss.domain.blockchain.domain.BlockChain;
import pss.clone.pss.domain.hospital.domain.HospitalMeta;
import pss.clone.pss.domain.hospital.domain.HospitalPage;
import pss.clone.pss.domain.model.WorkID;
import pss.clone.pss.domain.user.domain.CustomUserDetails;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class Commons {

    private static String activeProfile;

    private static String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    @Value("${spring.profiles.active}") //프로파일 여러개 사용시 active 된 프로파일 읽어오기(properteis 파일에서) https://freehoon.tistory.com/81
    private void init(String profile) {activeProfile = profile;}

    public static String getVpnUrl(String url) {
        if(activeProfile != null && activeProfile.equals("local")) {
            return "http://localhost:8080" + url;
        }

        HospitalMeta meta = getMeta();
        return "http://" + meta.getVpnUrl() + ":" + meta.getVpnPort() +url;
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HospitalMeta getMeta() {
        HttpServletRequest req = getRequest();
        HospitalMeta meta = (HospitalMeta) req.getSession().getAttribute("meta"); //setattribute 에 저장되어있으면그걸 꺼내옴

        return meta;

    }

    public static String SystemDataTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    public static CustomUserDetails GetLoginUser() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static HospitalPage getHospitalPage() {
        HttpServletRequest req = getRequest();
        HospitalPage page = (HospitalPage) req.getSession().getAttribute("page");

        if(page ==null) {
            return new HospitalPage(null, false, false, false, false, false);
        } else {
            return page;
        }

    }

    public static BlockChain CreateBlockChain(String reqDT, String resDT, String patChtNum, Integer comNum, WorkID workID) {
        String userCode = SecurityContextHolder.getContext().getAuthentication().getName();

        return new BlockChain(
                reqDT, resDT, patChtNum, comNum,
                workID.get(), "", userCode, ClientIP(), null);
    }

    private static String ClientIP() {
        if (RequestContextHolder.getRequestAttributes()==null) {
            return "0.0.0.0";
        }

        HttpServletRequest request = getRequest();
        for (String header: IP_HEADER_CANDIDATES) {
            String ipList = request.getHeader(header);
            if(ipList != null && ipList.length() != 0 && !"unknown".equalsIgnoreCase(ipList)) {
                String ip = ipList.split(",")[0];
                return ip;
            }
        }


        return request.getRemoteAddr(); //클라이언트 ip추출

    }

}
