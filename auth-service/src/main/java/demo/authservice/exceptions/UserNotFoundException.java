package demo.authservice.exceptions;

import demo.common.exception.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseBusinessException {

    public UserNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return "USER_NOT_FOUND";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
