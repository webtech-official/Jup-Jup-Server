package com.gsm.jupjup.advice.exception;

public class AlreadyReturnedException extends RuntimeException {
    public AlreadyReturnedException(String msg, Throwable t) {
        super(msg, t);
    }

    public AlreadyReturnedException(String msg) {
        super(msg);
    }

    public AlreadyReturnedException() {
        super();
    }
}