package pss.clone.pss.domain.blockchain.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BlockChainResponse {
    private boolean	success;
    private	String	message;
    private	int		code;

    public BlockChainResponse(boolean success, String message, int code) {
        this.success = success;
        this.message = message;
        this.code = code;
    }
}
