package br.com.training.exception;

import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class UserExceptionResponse implements Serializable {

    private final Instant timestamp;
    private final String status;
    private final String error;
    private final List<String> message;
    private final String path;

    public UserExceptionResponse(Instant timestamp, String status, String error, List<String> message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public List<String> getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "ExceptionResponse{" +
                "timestamp=" + timestamp +
                ", status='" + status + '\'' +
                ", error='" + error + '\'' +
                ", message=" + message +
                ", path='" + path + '\'' +
                '}';
    }

}
