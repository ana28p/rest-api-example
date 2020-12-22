package movie.quotes.example.exception;

import movie.quotes.example.util.Message;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityResourceNotFoundException.class)
    public ResponseEntity<Object> handleEntityResourceNotFound(final EntityResourceNotFoundException ex) {
        ApiError error = createError(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST, ex);
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex) {
        ApiError error = createError(Message.INVALID_PARAMETERS, HttpStatus.BAD_REQUEST, ex);
        return new ResponseEntity<>(error, error.getStatus());
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
        ApiError error = createError(Message.INVALID_METHOD_ARGS, HttpStatus.BAD_REQUEST, ex);
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        logger.error("error", ex);
        //
        ApiError error = createError(Message.SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        return new ResponseEntity<>(error, error.getStatus());
    }

    private ApiError createError(String message, HttpStatus status, Exception exception) {
        ApiError error = new ApiError(status);
        error.setMessage(message);
        error.setDebugMessage(exception.getMessage());
        return error;
    }
}
