package com.mini.auction.entity.base.exception.CommentExceptions;

public class NotAuthorException extends RuntimeException {
    public NotAuthorException(String username) {
        super(username);
    }
}
