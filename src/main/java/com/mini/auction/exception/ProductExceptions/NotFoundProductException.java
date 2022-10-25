package com.mini.auction.exception.ProductExceptions;

public class NotFoundProductException extends RuntimeException {
    public NotFoundProductException(String s) {
        super(s);
    }
}
