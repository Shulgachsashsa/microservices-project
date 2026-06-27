package demo.authservice.exceptions.handler;

import demo.common.dto.response.ErrorResponse;
import demo.common.exception.BaseBusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(BaseBusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BaseBusinessException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(e.getErrorCode())
                .userMessage(e.getMessage())
                .build();
        return ResponseEntity.status(e.getHttpStatus()).body(errorResponse);
    }

}
