package pss.clone.pss.domain.blockchain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import pss.clone.pss.domain.blockchain.dao.BlockChainMapper;
import pss.clone.pss.domain.blockchain.domain.BlockChain;
import pss.clone.pss.domain.blockchain.dto.BlockChainResponse;
import pss.clone.pss.domain.blockchain.dto.BlockChainTokenResponse;
import pss.clone.pss.domain.hospital.domain.HospitalMeta;
import pss.clone.pss.global.common.Commons;
import pss.clone.pss.global.common.RandomKeyGenerator;
import pss.clone.pss.global.error.exception.InternalServerErrorException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlockChainSaveService {

    private final BlockChainMapper blockChainMapper;
    private final RestTemplate blockChainTemplate;

    @Transactional
    public void save(BlockChain blockChain, Object req, Object res) {

        HospitalMeta meta = Commons.getMeta();
        String msgID = RandomKeyGenerator.nextSeqNumberKey();

        blockChain.setMsgID(msgID);
        blockChain.setSvceDomain("tenant7");

        blockChainMapper.save(blockChain);

        sendBlockChain(msgID, req, res, meta);
    }

    private void sendBlockChain(String msgID, Object req, Object res, HospitalMeta meta) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); //헤더 전송 타입 json으로 설정
        headers.setBearerAuth(getBearerToken("tenant7", "admin@Tenant7")); //setbearerauth : token 운반자 설정

        List<String> args = new ArrayList<>();
        args.add(msgID);
        args.add(Object2Json(req));
        args.add(Object2Json(res));

        Map<String, Object> param = new HashMap<>();
        param.put("fcn", "fnInvokeCareme");
        param.put("args", args);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(param, headers);

        BlockChainResponse response = blockChainTemplate
                .postForObject(
                        "/channels/chtenant7/chaincodes/cc_msystech",
                        entity,
                        BlockChainResponse.class
                );

        if(!response.isSuccess()) {
            throw new InternalServerErrorException(response.getMessage());
        }
    }

    private String getBearerToken(String orgName, String username) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> param = new HashMap<>();
        param.put("orgName", orgName);
        param.put("username", username);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(param, headers);

        BlockChainTokenResponse response = blockChainTemplate
                .postForObject(
                        "/users",
                        entity,
                        BlockChainTokenResponse.class);

        if(!response.isSuccess()) {
            throw new InternalServerErrorException(response.getMessage());
        }
        return response.getToken();
    }

    private String Object2Json(Object obj) {
        if(obj == null) {
            return "";
        }
        String jsonString ="";

        ObjectMapper mapper = new ObjectMapper(); //jackson json의 objectmapper을 이용하여 java object를 json문자열로 변환하기

        try {
            jsonString = mapper.writeValueAsString(obj);//obj를 string타입으로 변환
            log.info("blockChain:JsonString:{}", jsonString);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return jsonString;
    }
}
