package demo.common.exception;

import org.springframework.http.HttpStatus;

public abstract class BaseBusinessException extends RuntimeException {

    protected BaseBusinessException(String message) {
        super(message);
    }

    public abstract String getErrorCode();

    public abstract HttpStatus getHttpStatus();
}
