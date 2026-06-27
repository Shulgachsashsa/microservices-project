package demo.authservice.exceptions;

import demo.common.exception.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class RegistrationExpiredOrNotFoundException extends BaseBusinessException {

    public RegistrationExpiredOrNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return "REGISTRATION_EXPIRED_OR_NOT_FOUND";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
