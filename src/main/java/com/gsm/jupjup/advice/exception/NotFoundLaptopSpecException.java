package com.gsm.jupjup.advice.exception;

public class NotFoundLaptopSpecException extends RuntimeException{
    public NotFoundLaptopSpecException(String msg, Throwable t) {
        super(msg, t);
    }

    public NotFoundLaptopSpecException(String msg) {
        super(msg);
    }

    public NotFoundLaptopSpecException() {
        super();
    }
}
