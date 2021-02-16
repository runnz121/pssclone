package pss.clone.pss.global.error.exception;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ErrorCode errorcode;

    public BusinessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorcode = errorCode;
    }

    public BusinessException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorcode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorcode;
    }
}
