package com.gsm.jupjup.advice.exception;

public class EquipmentDuplicateException extends RuntimeException {
    public EquipmentDuplicateException(String msg, Throwable t) {
        super(msg, t);
    }

    public EquipmentDuplicateException(String msg) {
        super(msg);
    }

    public EquipmentDuplicateException() {
        super();
    }
}