package com.mini.auction.exception.bidException;

public class AlreadyStartBidException extends RuntimeException {
    public AlreadyStartBidException(String s) {
        super(s);
    }
}
