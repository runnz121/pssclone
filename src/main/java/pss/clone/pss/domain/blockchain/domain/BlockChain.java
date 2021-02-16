package pss.clone.pss.domain.blockchain.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class BlockChain {

    private String	msgID;
    private	String	reqDT;
    private	String	resDT;
    private	String	patChtNum;
    private Integer	comNum;
    private	String	workID;
    private String	workCategory;
    private	String	reqID;
    private	String	reqIP;
    private String	svceDomain;

    public BlockChain(String reqDT, String resDT, String patChtNum, Integer comNum, String workID, String workCategory, String reqID,
                      String reqIP, String svceDomain) {
        this.reqDT = reqDT;
        this.resDT = resDT;
        this.patChtNum = patChtNum;
        this.comNum = comNum;
        this.workID = workID;
        this.workCategory = workCategory;
        this.reqID = reqID;
        this.reqIP = reqIP;
        this.svceDomain = svceDomain;
    }

    public BlockChain(String msgID, String reqDT, String resDT, String patChtNum, Integer comNum, String workID,
                      String workCategory, String reqID, String reqIP, String svceDomain) {
        this.msgID = msgID;
        this.reqDT = reqDT;
        this.resDT = resDT;
        this.patChtNum = patChtNum;
        this.comNum = comNum;
        this.workID = workID;
        this.workCategory = workCategory;
        this.reqID = reqID;
        this.reqIP = reqIP;
        this.svceDomain = svceDomain;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public void setSvceDomain(String svceDomain) {
        this.svceDomain = svceDomain;
    }

}