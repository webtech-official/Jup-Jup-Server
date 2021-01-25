package com.gsm.jupjup.advice.exception;

public class EquipmentNotFoundException extends RuntimeException {
    public EquipmentNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public EquipmentNotFoundException(String msg) {
        super(msg);
    }

    public EquipmentNotFoundException() {
        super();
    }
}