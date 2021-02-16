package pss.clone.pss.domain.consent.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pss.clone.pss.domain.consent.domain.Consent;
import pss.clone.pss.domain.consent.service.BookmarkSaveService;
import pss.clone.pss.domain.consent.service.ConsentFindService;

import java.util.List;

@RestController //view가 필요없는 api만 지원하는 것 https://gmlwjd9405.github.io/2018/12/25/difference-dao-dto-entity.html
@RequiredArgsConstructor
public class ConsentApi {

    private final ConsentFindService consentFindService;
    private final BookmarkSaveService bookmarkSaveService;

    @GetMapping("/cosents")
    public List<Consent> consents() {
        return consentFindService.consents();
    }

    @PostMapping("/bookmarks/{code}")
    public void addBookmark(@PathVariable String code) {
        bookmarkSaveService.addBookmark(code);
    }

    @DeleteMapping("/bookmakrs/{code}")
    public void removeBookmark(@PathVariable String code) {
        bookmarkSaveService.removeBookmark(code);
    }

}
