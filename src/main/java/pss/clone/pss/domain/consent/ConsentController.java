package pss.clone.pss.domain.consent;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import pss.clone.pss.domain.patient.domain.Patient;
import pss.clone.pss.domain.patient.dto.PatientCome;
import pss.clone.pss.domain.user.service.UserFindService;


//https://velog.io/@agugu95/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%8C%A8%ED%84%B4%EA%B3%BC-DAO-DTO-Repository

@Controller
@RequiredArgsConstructor
@SessionAttributes("patient")//model 정보를 httpsession에 저장시켜준다, sessionattribute에 지정된 key 와 model에 저장된 key값이 같으면 자동으로 session에 저장  //https://goodgid.github.io/Spring-MVC-SessionAttributes/
public class ConsentController {

    private final UserFindService userFindService;

    @GetMapping("/consent")
    public ModelAndView consent(@ModelAttribute("patient") PatientCome patient) { //https://donggu1105.tistory.com/14 1.patient 객체를 생성 2.patient 오브젝트 http로 넘어온 값을 자동 바인딩 3.view단의 patient로 호출된 값을 전달
        ModelAndView mav = new ModelAndView("/consent/elec_consent");

        if(patient != null) {
            mav.addObject("chartNumber", patient.getPatChtNum());
            mav.addObject("name", patient.getPatNam());
        }
        return mav;
    }

    @GetMapping("/consent/write")
    public ModelAndView write() {
        ModelAndView mav = new ModelAndView("/consent/write");
        mav.addObject("userList", userFindService.users());

        return mav;
    }

    @GetMapping("/consent/list")
    public ModelAndView list(String code, @ModelAttribute("patient") PatientCome patient) {
        ModelAndView mav = new ModelAndView("/consent/list");

        if(patient != null) {
            mav.addObject("chartNumber", patient.getPatChtNum());
            mav.addObject("name", patient.getPatNam());
        }
        if(code ==null || code.length() <=0) {
            mav.addObject("code","E");
        }else {
            mav.addObject("code", code);
        }
        return mav;
    }

}
