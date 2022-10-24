package com.mini.auction.exception;

import com.mini.auction.exception.MemberExceptions.BadPasswordConfirmException;
import com.mini.auction.exception.MemberExceptions.DuplicateMemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    //중복 아이디 존재 여부
    @ExceptionHandler(DuplicateMemberException.class)
    public ResponseEntity<ErrorResponse> handlerDuplicateMemberException(Exception e){
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.CONFLICT, ErrorCode.DUPLICATE_MEMBER_ID.getMessage(), ErrorCode.DUPLICATE_MEMBER_ID.getDetail());
        log.error("error : {}, stacktrace: {}", errorResponse, e.getStackTrace());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    //비빌번호 확인
    @ExceptionHandler(BadPasswordConfirmException.class)
    public ResponseEntity<ErrorResponse> handlerBadPasswordConfirmException(Exception e){
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, ErrorCode.BAD_PASSWORD_CONFIRM.getMessage(), ErrorCode.BAD_PASSWORD_CONFIRM.getDetail());
        log.error("error:{}, stacktrace:{}", errorResponse, e.getStackTrace());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public void handlerUsernameNotFoundException(Exception e) {
        ErrorResponse
    }

    /**
     * @Valid 에서 발생하는 예외 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException exception) {

        List<Error> errors = new ArrayList<>();

        for(FieldError field : exception.getBindingResult().getFieldErrors()) {
            errors.add(new Error(HttpStatus.BAD_REQUEST, field.getDefaultMessage()));
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }
}
