package demo.authservice.exceptions;

import demo.common.exception.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class UserWithEmailAlreadyExists extends BaseBusinessException {

    public UserWithEmailAlreadyExists(String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return "USER_WITH_EMAIL_ALREADY_EXISTS";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
