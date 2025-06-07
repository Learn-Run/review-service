package com.unionclass.reviewservice.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseErrorResponse> handleBaseException(BaseException e) {
        ErrorCode errorCode = e.getErrorCode();
        BaseErrorResponse response = BaseErrorResponse.of(
                errorCode.getHttpStatus(),
                errorCode.isSuccess(),
                errorCode.getCode(),
                errorCode.getMessage());
        return new ResponseEntity<>(response, errorCode.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String errorMessage = error == null ? "알 수 없는 에러가 발생했습니다." : error.getField() + " 이(가) " + error.getDefaultMessage();
        BaseErrorResponse response = BaseErrorResponse.of(HttpStatus.BAD_REQUEST, false, 999, errorMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<BaseErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        ErrorCode code = ErrorCode.INTERNAL_SERVER_ERROR;
        log.warn("DB 제약 조건 위반", e);
        return new ResponseEntity<>(
                BaseErrorResponse.of(code.getHttpStatus(), false, code.getCode(), "DB 제약 조건에 위배되었습니다."),
                code.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseErrorResponse> handleException(Exception e) {
        log.error("handleException: {}", e.getMessage(), e);
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        BaseErrorResponse response = BaseErrorResponse.of(
                errorCode.getHttpStatus(),
                errorCode.isSuccess(),
                errorCode.getCode(),
                e.getMessage());
        return new ResponseEntity<>(response, errorCode.getHttpStatus());
    }
}
