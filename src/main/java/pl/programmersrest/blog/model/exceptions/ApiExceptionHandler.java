package pl.programmersrest.blog.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.programmersrest.blog.model.exceptions.custom.PostNotFoundException;
import pl.programmersrest.blog.model.exceptions.custom.TitleTakenException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<Object> handlePostNotFoundException(PostNotFoundException ex){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex, "There's no post with this id");
        return buildApiError(apiError);
    }
    @ExceptionHandler(TitleTakenException.class)
    public ResponseEntity<Object> handleTitleTakenException(TitleTakenException ex){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,ex,"Title need to bo unique");
        return buildApiError(apiError);
    }

    private ResponseEntity<Object> buildApiError(ApiError apiError){
        return new ResponseEntity<>(apiError, apiError.getStatusCode());
    }
}