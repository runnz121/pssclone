package pss.clone.pss.domain.consent.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import pss.clone.pss.domain.consent.service.ConsentFindService;

@RestController //view가 필요없는 api만 지원하는 것 https://gmlwjd9405.github.io/2018/12/25/difference-dao-dto-entity.html
@RequiredArgsConstructor
public class ConsentApi {

    private final ConsentFindService
}
