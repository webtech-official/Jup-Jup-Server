package com.gsm.jupjup.advice.exception;

public class NotFoundLaptopException extends RuntimeException {
    public NotFoundLaptopException(String msg, Throwable t) {
        super(msg, t);
    }

    public NotFoundLaptopException(String msg) {
        super(msg);
    }

    public NotFoundLaptopException() {
        super();
    }
}