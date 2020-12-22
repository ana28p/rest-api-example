package movie.quotes.example.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import movie.quotes.example.util.FormatPattern;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
class ApiError {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPattern.DATE_FORMAT)
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private String path;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    ApiError(HttpStatus status, Throwable cause) {
        this(status);
        this.message = "Unexpected error";
        this.debugMessage = cause.getLocalizedMessage();
    }

    ApiError(HttpStatus status, String message, Throwable cause) {
        this(status, cause);
        this.message = message;
    }

    ApiError(HttpStatus status, String message, String path, Throwable cause) {
        this(status, message, cause);
        this.path = path;
    }

}
