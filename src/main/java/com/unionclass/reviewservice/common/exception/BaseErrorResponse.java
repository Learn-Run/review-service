package com.unionclass.reviewservice.common.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseErrorResponse {

    private final HttpStatus httpStatus;
    private final boolean isSuccess;
    private final int code;
    private final String message;

    @Builder
    public BaseErrorResponse(HttpStatus httpStatus, boolean isSuccess, int code, String message) {
        this.httpStatus = httpStatus;
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

    public static BaseErrorResponse of(HttpStatus httpStatus, boolean isSuccess, int code, String message) {
        return BaseErrorResponse.builder()
                .httpStatus(httpStatus)
                .isSuccess(isSuccess)
                .code(code)
                .message(message)
                .build();
    }
}
