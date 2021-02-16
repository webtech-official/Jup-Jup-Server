package com.gsm.jupjup.advice.exception;

public class EmailNotVerifiedException extends RuntimeException{
    public EmailNotVerifiedException(String msg, Throwable t) {
        super(msg, t);
    }

    public EmailNotVerifiedException(String msg) {
        super(msg);
    }

    public EmailNotVerifiedException() {
        super();
    }
}
