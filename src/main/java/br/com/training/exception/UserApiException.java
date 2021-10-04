package br.com.training.exception;

public class UserApiException extends RuntimeException {
    private static final long serialVersionUID = 1L;

//    public UserApiException(String msg, Throwable cause) {
//        super(msg, cause);
//    }
//
    public UserApiException(UserErrorDetail userErrorDetail) {
        super(userErrorDetail != null ? userErrorDetail.toString() : null);
    }

    public UserApiException(String msg) {
        super(msg);
    }

//    public UserApiException(Throwable cause) {
//        super(cause);
//    }
}
