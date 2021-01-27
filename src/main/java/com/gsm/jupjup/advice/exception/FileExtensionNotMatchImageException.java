package com.gsm.jupjup.advice.exception;

public class FileExtensionNotMatchImageException extends RuntimeException{
    public FileExtensionNotMatchImageException(String msg, Throwable t) {
        super(msg, t);
    }

    public FileExtensionNotMatchImageException(String msg) {
        super(msg);
    }

    public FileExtensionNotMatchImageException() {
        super();
    }
}
