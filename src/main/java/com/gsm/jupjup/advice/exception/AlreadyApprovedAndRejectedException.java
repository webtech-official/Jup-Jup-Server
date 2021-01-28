package com.gsm.jupjup.advice.exception;

public class AlreadyApprovedAndRejectedException extends RuntimeException {
    public AlreadyApprovedAndRejectedException(String msg, Throwable t) {
        super(msg, t);
    }

    public AlreadyApprovedAndRejectedException(String msg) {
        super(msg);
    }

    public AlreadyApprovedAndRejectedException() {
        super();
    }
}