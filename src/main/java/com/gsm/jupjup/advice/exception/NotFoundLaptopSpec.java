package com.gsm.jupjup.advice.exception;

public class NotFoundLaptopSpec extends RuntimeException{
    public NotFoundLaptopSpec(String msg, Throwable t) {
        super(msg, t);
    }

    public NotFoundLaptopSpec(String msg) {
        super(msg);
    }

    public NotFoundLaptopSpec() {
        super();
    }
}
