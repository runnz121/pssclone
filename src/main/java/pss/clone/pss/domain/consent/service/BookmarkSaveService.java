package pss.clone.pss.domain.consent.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pss.clone.pss.global.common.Commons;

@Service
@RequiredArgsConstructor
public class BookmarkSaveService {

    private final RestTemplate localTestTemplate;

    public void addBookmark(String code) {
        String loginUser = SecurityContextHolder.getContext().getAuthentication().getName();

        localTestTemplate
                .postForObject(
                        Commons.getVpnUrl("/api/consents/bookmarks/{userCode}/{code}"),
                        null,
                        Void.class,
                        loginUser, code);
    }

    public void removeBookmark(String code) {
        String loginUser = SecurityContextHolder.getContext().getAuthentication().getName();

        localTestTemplate
                .delete(
                        Commons.getVpnUrl("/api/consents/bookmarks/{userCode}/{code}"),
                        loginUser, code
                );
    }
}
