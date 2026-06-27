package demo.authservice.exceptions;

import demo.common.exception.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class IncorrectVerificationCodeException extends BaseBusinessException {

    public IncorrectVerificationCodeException(String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return "INCORRECT_VERIFICATION_CODE";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
