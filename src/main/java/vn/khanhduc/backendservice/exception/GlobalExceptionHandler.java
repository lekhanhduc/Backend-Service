package vn.khanhduc.backendservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.khanhduc.backendservice.dto.response.ErrorResponse;
import vn.khanhduc.backendservice.dto.response.ResponseData;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    ResponseEntity<ResponseData<Object>> handlingAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();

        ResponseData<Object> responseData = ResponseData.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.status(errorCode.getHttpStatus()).body(responseData);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> handlingMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<String> errors = fieldErrors.stream().map(FieldError::getDefaultMessage).toList();

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        errorResponse.setMessage(errors.size() > 1 ? String.valueOf(errors) : errors.get(0));

        return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ErrorResponse> handlingResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
        ErrorResponse response = new ErrorResponse();

        response.setTimestamp(new Date());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setPath(request.getRequestURI());
        response.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        response.setMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
