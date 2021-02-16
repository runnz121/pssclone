package pss.clone.pss.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pss.clone.pss.domain.user.domain.Department;
import pss.clone.pss.domain.user.domain.User;
import pss.clone.pss.global.common.Commons;

import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserFindService {

    private final RestTemplate localTestTemplate; //api호출을 위한 객체 생성

    public User getByUserCode(String userCode) {
        return localTestTemplate.getForObject(
                Commons.getVpnUrl("/api/users/{userCode}"),
                User.class,
                userCode);
    }

    public List<Department> departments(String depCod) {
        Department[] list = localTestTemplate.getForObject( //주어진 URL 주소로 HTTP GET 메서드로 객체로 결과를 반환받는다
                Commons.getVpnUrl("/api/departments"),
                Department[].class); //.class를 이용해서 Department[]에서 다루는 모든 정보를 얻어올 수 있다.

        if (depCod != null && !depCod.isEmpty()) {
            return loginUserCheck(depCod, list);
        }


        return Arrays.asList(list);
    }

    public List<User> doctors(String uidCod) {
        User[] list = localTestTemplate.getForObject(
                Commons.getVpnUrl("/api/users?type=dtr"), //? : get 변수가 붙는 자리 //querystring시작을 의미(db호출 명령 즉 type=dtr을 호출 )
                User[].class
        );

        if(uidCod !=null && !uidCod.isEmpty()) {
            return loginUserCheck(uidCod, list);
        }

        return Arrays.asList(list);
    }

    public List<User> users() {
        User[] list = localTestTemplate.getForObject(
                Commons.getVpnUrl("/api/users"),
                User[].class
        );

        String loginUser = SecurityContextHolder.getContext().getAuthentication().getName();
        return loginUserCheck(loginUser, list);
    }

    private List<Department> loginUserCheck(String depCod, Department[] list) {
        for (Department dept : list) {
            dept.isLoginDept(depCod);
        }
        return Arrays.asList(list);
    }

    private List<User> loginUserCheck(String uidCod, User[] list) {
        for (User user : list) {
            user.isLoginUser(uidCod);
        }
        return Arrays.asList(list);
    }


}
