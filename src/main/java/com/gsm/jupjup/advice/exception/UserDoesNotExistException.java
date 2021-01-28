package com.gsm.jupjup.advice.exception;
public class UserDoesNotExistException  extends RuntimeException{
    public UserDoesNotExistException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserDoesNotExistException(String msg) {
        super(msg);
    }

    public UserDoesNotExistException() {
        super();
    }
}
