package pss.clone.pss.domain.patient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import pss.clone.pss.domain.hospital.service.HospitalFindService;
import pss.clone.pss.domain.user.domain.CustomUserDetails;
import pss.clone.pss.domain.user.service.UserFindService;
import pss.clone.pss.global.common.Commons;


@Controller
@RequiredArgsConstructor
public class PatientController {

    private final HospitalFindService hospitalFindService;
    private final UserFindService userFindService;



    @GetMapping("/patient/search")
    public ModelAndView serch() {
        ModelAndView mav = new ModelAndView("/patient/search");
        String uidCod = null;
        String depCod = null;
        CustomUserDetails user = Commons.GetLoginUser();

        if(user != null) {
            uidCod = user.getUsername();
            depCod = user.getDepCod();
        }

        mav.addObject("wardList", hospitalFindService.wards());

        mav.addObject("deptList", userFindService.departments(depCod));
        mav.addObject("doctList", userFindService.doctors(uidCod));

        return mav;
    }

    @GetMapping("/patient/result")
    public ModelAndView patientRsult(String dep, String dtr){
        ModelAndView mav = new ModelAndView("/patient/result");

        mav.addObject("deptList", userFindService.departments(dep));
        mav.addObject("doctList", userFindService.doctors(dtr));

        return mav;
    }

    @GetMapping("/safety/{page}")
    public ModelAndView safety(@PathVariable int page, String chartNumber, String name, String input) {
        ModelAndView mav = new ModelAndView();

        switch(page) {
            case 1:
                mav.setViewName("/safety/safety_step1");
                break;
            case 2:
                mav.setViewName("/safety/safety_step2");
                mav.addObject("name",name);
                mav.addObject("chartNumber", chartNumber);
                break;
            case 3:
                mav.setViewName("/safety/safety_step3");
                mav.addObject("name", name);
                mav.addObject("chartNumber", chartNumber);
                mav.addObject("input", input);
                break;
            default:
        }

        return mav;
    }



}
