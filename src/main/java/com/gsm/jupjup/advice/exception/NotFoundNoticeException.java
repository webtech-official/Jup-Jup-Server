package com.gsm.jupjup.advice.exception;

public class NotFoundNoticeException extends RuntimeException{
    public NotFoundNoticeException(String msg, Throwable t) {
        super(msg, t);
    }

    public NotFoundNoticeException(String msg) {
        super(msg);
    }

    public NotFoundNoticeException() {
        super();
    }
}
