package pss.clone.pss.global.error.exception;

public class InternalServerErrorException extends BusinessException{

    private static final long serialVersionUID = 1L;

    public InternalServerErrorException(String message) {
        super(message, ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
