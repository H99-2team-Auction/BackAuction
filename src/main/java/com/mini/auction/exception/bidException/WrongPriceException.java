package com.mini.auction.exception.bidException;

public class WrongPriceException extends RuntimeException {
    public WrongPriceException(String s) {
        super(s);
    }
}
