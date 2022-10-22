package com.mini.auction.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private int errorCode;
    private String errorMessage;
    private String errorDetail;

    public static ErrorResponse of(HttpStatus httpStatus, String message, String errorDetail) {
        return new ErrorResponse(httpStatus.value(), message, errorDetail);
    }
}