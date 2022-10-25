package com.mini.auction.exception.bidException;

public class AlreadySoldOutException extends RuntimeException {
    public AlreadySoldOutException(String s) {
        super(s);
    }
}
