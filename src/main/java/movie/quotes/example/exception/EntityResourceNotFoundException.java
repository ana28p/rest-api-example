package movie.quotes.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityResourceNotFoundException extends RuntimeException {

    public EntityResourceNotFoundException() {
        super();
    }

    public EntityResourceNotFoundException(String message) {
        super(message);
    }

    public EntityResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}