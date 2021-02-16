package pss.clone.pss.domain.blockchain.aop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pss.clone.pss.domain.blockchain.annotation.Blockchain;
import pss.clone.pss.domain.blockchain.domain.BlockChain;
import pss.clone.pss.domain.blockchain.service.BlockChainSaveService;
import pss.clone.pss.global.common.Commons;
import pss.clone.pss.global.error.exception.InternalServerErrorException;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Aspect
@Component//빈으로 등록
@RequiredArgsConstructor//이 어노테이션은 초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성해 줍니다. 주로 의존성 주입(Dependency Injection) 편의성을 위해서 사용되곤 합니다.
public class BlockchainAopAdvice {

    private final BlockChainSaveService blockChainSaveService;

    //@annotation은 pointcut역할 : 어디에 적용시킬것인지 대상 지정
    @Around("@annotation(pss.clone.pss.domain.blockchain.annotation.Blockchain)") //around : 메소드 실행전과 후 모두 실행
    public Object processBlockchainAnnotation(ProceedingJoinPoint pj) throws Throwable { //https://snoopy81.tistory.com/295
        if (!Commons.getHospitalPage().isBlockchain()) {
            return pj.proceed();
        }

        MethodSignature ms = (MethodSignature) pj.getSignature(); //호출되는 매소드 정보를 구한다

        Blockchain bc = ms.getMethod().getAnnotation(Blockchain.class);
        String reqDT = Commons.SystemDataTime();

        Object response = pj.proceed();

        String resDT = Commons.SystemDataTime();
        String[] names = ms.getParameterNames();
        Object[] values = pj.getArgs();

        Map<String, Object> param = toParamMap(names, values);
        if (values[0] instanceof List) { //객체 instanceof 클래스 // 두 클래스간 형변환이 가능한지 확인 true,false로 리턴

            listParamSave(bc, reqDT, response, resDT, names, param);

        } else {
            String chartNumber = getChartNumber(param, response);
            Integer comeNumber = getComeNumber(param, response);

            BlockChain blockChain = Commons.CreateBlockChain(reqDT, resDT, chartNumber, comeNumber, bc.workID());
            blockChainSaveService.save(blockChain, param, response);
        }
        return response;
    }

    private void listParamSave(Blockchain bc, String reqDT, Object response, String resDT, String[] names,
                               Map<String, Object> param) {
        List<?> list = (List<?>)param.get(names[0]);

        for(Object obj : list) {
            ObjectMapper om = new ObjectMapper();
            Map<String, Object> params = om.convertValue(obj, new TypeReference<Map<String, Object>>() {
            });

            String chartNumber = getChartNumber(params, response);
            Integer comeNumber = getComeNumber(params, response);

            BlockChain blockchain = Commons.CreateBlockChain(reqDT, resDT, chartNumber, comeNumber, bc.workID());
            blockChainSaveService.save(blockchain, param, response);

        }
    }

    private String getChartNumber(Map<String, Object> params, Object response) {
        Set<String> paramKeys = null;

        if (params != null) {
            paramKeys = params.keySet();
        }

        if (paramKeys != null && paramKeys.contains("chartNumber")) {
            return params.get("chartNumber").toString();
        }

        if (paramKeys != null && paramKeys.stream().anyMatch(k -> k.matches("^\\w*ChtNum$"))) { // :^ : 문자열 시작  \ : 역슬래시 : 확장문자 , * : 앞문자가 있을수도 없을수도 있음 $ : 문자열 종료 https://tristan91.tistory.com/463
            String paramName =
                    paramKeys.stream()
                            .filter(k -> k.matches("^\\w*ChtNum$"))
                            .collect(Collectors.toList()).get(0);

            return params.get(paramName).toString();
        }

        if (response != null) {
            if (response instanceof List) {
                List<?> list = (List<?>) response;
                if (list.isEmpty())
                    return "";

                response = list.get(0);
            }

            ObjectMapper om = new ObjectMapper();
            Map<String, String> responseMap =
                    om.convertValue(response, new TypeReference<Map<String, String>>() {
                    });

            List<String> chtNum = responseMap.keySet() //list에서 새로운 list를 생성할 수 있다.
                    .stream()
                    .map(k -> k.matches("^\\w*ChtNum$") ? responseMap.get(k) : "") //matches() 대상 문자열과 패턴이 일치할 경우 true 반환  https://coding-factory.tistory.com/529
                    .collect(Collectors.toList());                                       //map : 특정조건에 맞게 해당 조건으로 변환시켜준다.

            return chtNum.get(0);
        }

        return "";
    }

    private Integer getComeNumber(Map<String, Object> params, Object response) {
        Set<String> paramKeys = null;

        if(paramKeys !=null && paramKeys.contains("comeNumber")) {
            return Integer.parseInt(params.get("comeNumber").toString());
        }

        if(paramKeys != null && paramKeys.stream().anyMatch(k -> k.matches("^\\w*ComNum$"))){ //anymatch : 맞는개 1개라도 있는지
            String paramName = paramKeys.stream()
                    .filter(k -> k.matches("^\\w*ComNum$")) //filter : 특정 조건을 기준으로 맞는것을 걸러낸다.
                    .collect(Collectors.toList()).get(0);

            return Integer.parseInt(params.get(paramName).toString());
        }

        if(response != null) {
            if(response instanceof List) {
                List<?> list = (List<?>) response;
                if(list.isEmpty())
                    return null;

                response = list.get(0);
            }
            ObjectMapper om = new ObjectMapper();
            Map<String, String> responseMap =
                    om.convertValue(response, new TypeReference<Map<String, String>>() {}); //mapper.converValue(변환전 값, 변환후 클래스) 값을 map에 넣음 //https://stackoverflow.com/questions/2525042/how-to-convert-a-json-string-to-a-mapstring-string-with-jackson-json
                    //json library 사용시 json string을 map으로 변환시 typerefeerence 객체로 불러옴

            List<Integer> comNum = responseMap.keySet() //keyset() :map값을 전체 출력 하가는 경우 사용(key값만 필요할시)  https://tychejin.tistory.com/31
                .stream()
                .map(k -> k.matches("^\\w*ComNum$") ? Integer.parseInt(responseMap.get(k)) : null)
                .collect(Collectors.toList());

            if(!comNum.isEmpty())   { //isEmpty : 리스트가 특정 객체를 담고있다면 true를 반환
                return comNum.get(0);
            }
        }
        return null;
    }

    private Map<String, Object> toParamMap(String[] names, Object[] values) {
        if(names.length != values.length) {
            return null;
        }

        return IntStream.range(0, names.length) //Intstream : 원시형 데이터를 stream으로 다룰 수 있게 해줌
                .boxed() //intstream 요소를 박싱함
                .collect(Collectors.toMap( // tomap : 조건을 받아 map으로 모은다
                        i -> names[i] == null ? "" : names[i], //names 리턴

                        i -> {                                  //values 리턴
                            Object result ="";
                            try {
                                result = values[i] == null ? "" :
                                        (values[i] instanceof MultipartFile) ?
                                                toHex(((MultipartFile)values[i]).getBytes()) : //multipartfile.getbytes : 업로드한 파일 데이터 구하기
                                                values[i];
                            } catch (IOException e) {
                                throw new InternalServerErrorException(e.getMessage());
                            }
                            return result;
                        }));

        }

        private String toHex(byte[] bytes) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256"); //sha-256 알고리즘 이용
                md.update(bytes); //update()는 지정된 바이트 데이터를 사용해 다이제스트를 갱신합니다.


                StringBuilder sb = new StringBuilder(); //stringbuilder는 문자결함등을 수행할때 새로운 문자열을 만들지 않고 기존 문자열에 추가함
                for (byte b : md.digest()) { //digesst()는 바이트배열로 해쉬를 반환합니다, 패딩 등의 최종 처리를 행해 해시 계산을 완료 합니다.
                    sb.append(String.format("%02x", b));
                }
                return sb.toString();
            } catch (NoSuchAlgorithmException e) { //암호 알고리즘이 요구되었을 때 현재 환경에서 사용 불가할 경우 http://cris.joongbu.ac.kr/course/java/api/java/security/NoSuchAlgorithmException.html
                throw new InternalServerErrorException(e.getMessage());
            }
        }

}
