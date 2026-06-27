package demo.authservice.exceptions;

import demo.common.exception.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class MaxLimitedAttemptsException extends BaseBusinessException {

    public MaxLimitedAttemptsException(String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return "MAX_LIMITED_ATTEMPTS";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
