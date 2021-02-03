package pss.clone.pss.domain.consent;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;


//https://velog.io/@agugu95/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%8C%A8%ED%84%B4%EA%B3%BC-DAO-DTO-Repository

@Controller
@RequiredArgsConstructor
@SessionAttributes("patient")//model 정보를 httpsession에 저장시켜준다, sessionattribute에 지정된 key 와 model에 저장된 key값이 같으면 자동으로 session에 저장  //https://goodgid.github.io/Spring-MVC-SessionAttributes/
public class ConsentController {

    private final UserFindService userFindService;

    @GetMapping("/consent")
    public ModelAndView consent(@ModelAttribute("patient") PatientCome patient)


}
