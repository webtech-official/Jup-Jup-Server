package com.gsm.jupjup.advice.exception;

public class CDuplicateEmailException extends RuntimeException {

    public CDuplicateEmailException(String msg, Throwable t) {
        super(msg, t);
    }

    public CDuplicateEmailException(String msg) {
        super(msg);
    }

    public CDuplicateEmailException() {
        super();
    }

}
