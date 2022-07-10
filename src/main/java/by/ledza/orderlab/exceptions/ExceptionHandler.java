package by.ledza.orderlab.exceptions;

import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.Timestamp;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundedException.class)
    public ResponseEntity<ApiError> userNotFoundedExceptionHandler(UserNotFoundedException e){
        ApiError apiError = new ApiError();
        apiError.setMessage(e.getMessage());
        apiError.setStatus(400);

        return ResponseEntity.status(400).body(apiError);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(OrderException.class)
    public ResponseEntity<ApiError> orderExceptionHandler(OrderException e){
        ApiError apiError = new ApiError();
        apiError.setMessage(e.getMessage());
        apiError.setStatus(400);

        return ResponseEntity.status(400).body(apiError);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(VkTokenException.class)
    public ResponseEntity<ApiError> vkTokenExceptionHandler(VkTokenException e){
        ApiError apiError = new ApiError();
        apiError.setMessage(e.getMessage());
        apiError.setStatus(400);

        return ResponseEntity.status(400).body(apiError);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> exceptionHandler(RuntimeException e){
        ApiError apiError = new ApiError();
        apiError.setStatus(500);

        return ResponseEntity.status(500).body(apiError);
    }
}
