package by.ledza.orderlab.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.security.Timestamp;

@Data
public class ApiError {

    Integer status;

    String message;
}
