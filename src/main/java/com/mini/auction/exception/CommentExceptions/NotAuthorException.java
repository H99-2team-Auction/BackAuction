package com.mini.auction.exception.CommentExceptions;

public class NotAuthorException extends RuntimeException {
    public NotAuthorException(String username) {
        super(username);
    }
}
