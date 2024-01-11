package ntd.springboot.grademanagement.model.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AppApiException extends RuntimeException{
    @Getter
    private HttpStatus status;
    private String message;

    public AppApiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public AppApiException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
