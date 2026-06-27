package demo.authservice.exceptions;

import demo.common.exception.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class ExpiredJwtException extends BaseBusinessException {

    public ExpiredJwtException(String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return "AUTH_JWT_EXCEPTION";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
