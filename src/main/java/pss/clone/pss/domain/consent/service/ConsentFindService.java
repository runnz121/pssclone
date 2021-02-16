package pss.clone.pss.domain.consent.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pss.clone.pss.domain.consent.domain.Consent;
import pss.clone.pss.global.common.Commons;

import java.security.Security;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsentFindService {

    private final RestTemplate localTestTemplate;

    public List<Consent> consents() {
        String userCode = SecurityContextHolder.getContext().getAuthentication().getName();
        Consent[] list = localTestTemplate.getForObject(
                Commons.getVpnUrl("/api/consnets?userCode={userCode}"),
                Consent[].class,
                userCode);
        return Arrays.asList(list);
    }

    public String consent(String code) {
        String path = localTestTemplate.getForObject(
                Commons.getVpnUrl("/api/consents/{code}"),
                String.class,
                code);

        return path;
    }
}
