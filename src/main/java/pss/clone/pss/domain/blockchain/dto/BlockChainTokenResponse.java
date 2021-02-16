package pss.clone.pss.domain.blockchain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BlockChainTokenResponse {
    private boolean success;
    private	String	secret;
    private String	message;
    private	String	token;
    public BlockChainTokenResponse(boolean success, String secret, String message, String token) {
        this.success = success;
        this.secret = secret;
        this.message = message;
        this.token = token;
    }
}
