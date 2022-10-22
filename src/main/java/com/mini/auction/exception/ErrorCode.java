package com.mini.auction.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    BAD_PASSWORD_CONFIRM(HttpStatus.CONFLICT, "Password and PasswordConfirm don't match", "비밀번호와 비밀번호 확인이 다릅니다."),
    DUPLICATE_MEMBER_ID(HttpStatus.CONFLICT, "Member is duplicated", "중복된 사용자 ID가 존재합니다.");

    private final HttpStatus httpStatus;
    private final String message;
    private final String detail;
}
