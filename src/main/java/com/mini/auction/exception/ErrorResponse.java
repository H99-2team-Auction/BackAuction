package com.mini.auction.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private final int status;
    private final String message;
    private final String detail;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode){
        return ResponseEntity
                .status(errorCode.getHttpStatus()).body(
                        ErrorResponse.builder()
                                .status(errorCode.getHttpStatus())
                                .message(errorCode.getMessage())
                                .detail(errorCode.getDetail())
                                .build()
        );
    }
}