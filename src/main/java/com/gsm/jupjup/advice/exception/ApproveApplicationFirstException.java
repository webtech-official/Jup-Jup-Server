package com.gsm.jupjup.advice.exception;

public class ApproveApplicationFirstException extends RuntimeException {
    public ApproveApplicationFirstException(String msg, Throwable t) {
        super(msg, t);
    }

    public ApproveApplicationFirstException(String msg) {
        super(msg);
    }

    public ApproveApplicationFirstException() {
        super();
    }
}