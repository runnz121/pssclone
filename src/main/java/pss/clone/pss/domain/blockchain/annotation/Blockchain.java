package pss.clone.pss.domain.blockchain.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import pss.clone.pss.domain.model.WorkID;

@Target(ElementType.METHOD)//target하고 어디에 사용될지 지정(매소드에 사용한다)
@Retention(RetentionPolicy.RUNTIME)//해당 어노테이션이 살아있는 기간( runtime : 종료될떄까지 살아있음)
public @interface Blockchain {
    WorkID workID();
}
