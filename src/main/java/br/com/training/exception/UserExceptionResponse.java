package br.com.training.exception;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

public class UserExceptionResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Instant timestamp;
    private final int status;
    private final String error;
    private final List<Details> message;
    private final String path;

    public UserExceptionResponse(Instant timestamp, int status, String error, List<Details> message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public List<Details> getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "UserExceptionResponse{" +
                "timestamp=" + timestamp +
                ", status='" + status + '\'' +
                ", error='" + error + '\'' +
                ", message=" + message +
                ", path='" + path + '\'' +
                '}';
    }

    public static class Details {
        private String field;
        private String message;

        public Details(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }


        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "Details{" +
                    "field='" + field + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
