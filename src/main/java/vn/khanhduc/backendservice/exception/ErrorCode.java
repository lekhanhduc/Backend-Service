package vn.khanhduc.backendservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    UNAUTHORIZED(401, "Unauthorized", HttpStatus.UNAUTHORIZED),
    USER_NOT_EXISTED(404, "User not existed", HttpStatus.NOT_FOUND),
    ;
    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
