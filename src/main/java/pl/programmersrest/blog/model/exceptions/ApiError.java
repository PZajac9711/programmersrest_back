package pl.programmersrest.blog.model.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiError {
    private HttpStatus statusCode;
    private String message;
    private String debugMessage;

    public ApiError(HttpStatus statusCode, Throwable exception) {
        this.message = "Unexpected error";
        this.statusCode = statusCode;
        this.debugMessage = exception.getMessage();
    }

    public ApiError(HttpStatus statusCode, Throwable exception, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.debugMessage = exception.getMessage();
    }
}
