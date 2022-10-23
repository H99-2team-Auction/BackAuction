package com.mini.auction.exception;

public class WrongPriceException extends RuntimeException {
    public WrongPriceException(String message) {
        super(message);
    }
}
