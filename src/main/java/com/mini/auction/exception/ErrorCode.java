package com.mini.auction.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    /* BAD_REQUEST 400 error*/
    BAD_PASSWORD(HttpStatus.BAD_REQUEST.value(), "Password incorrect", "비밀번호를 확인하세요"),

    /*UNAUTHORIZED 401 error*/
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED_USER", "작성자만 수정 또는 삭제할 수 있습니다."),

    /*Not Found 404 error*/
    TITLE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Title is empty", "제목을 작성해 주세요"),
    LOW_PRICE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Low price is empty", "최저가를 입력해주세요"),
    CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Content is empty", "내용을 입력해 주세요"),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Product not found", "해당 상품은 존재하지 않습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Comment not found", "해당 상품의 댓글이 존재하지 않습니다."),

    /* CONFLICT 409 error*/
    BAD_PASSWORD_CONFIRM(HttpStatus.CONFLICT.value(), "Password and PasswordConfirm don't match", "비밀번호와 비밀번호 확인이 다릅니다."),
    DUPLICATE_MEMBER_ID(HttpStatus.CONFLICT.value(), "Member is duplicated", "중복된 사용자 ID가 존재합니다."),

    /*500 server error*/
    MEMBER_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Member not found", "멤버를 찾을 수 없습니다.");

    private final Integer httpStatus;
    private final String message;
    private final String detail;
}
