package pss.clone.pss.domain.blockchain.aop;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import pss.clone.pss.domain.blockchain.service.BlockChainSaveService;

@Aspect
@Component//빈으로 등록
@RequiredArgsConstructor//이 어노테이션은 초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성해 줍니다. 주로 의존성 주입(Dependency Injection) 편의성을 위해서 사용되곤 합니다.
public class BlockchainAopAdvice {

    private final BlockChainSaveService blockChainSaveService;
}
