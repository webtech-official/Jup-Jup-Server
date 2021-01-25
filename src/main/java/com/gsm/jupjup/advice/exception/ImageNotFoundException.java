package com.gsm.jupjup.advice.exception;

public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public ImageNotFoundException(String msg) {
        super(msg);
    }

    public ImageNotFoundException() {
        super();
    }
}