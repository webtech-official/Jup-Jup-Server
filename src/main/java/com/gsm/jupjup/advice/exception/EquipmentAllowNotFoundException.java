package com.gsm.jupjup.advice.exception;

public class EquipmentAllowNotFoundException extends RuntimeException {
    public EquipmentAllowNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public EquipmentAllowNotFoundException(String msg) {
        super(msg);
    }

    public EquipmentAllowNotFoundException() {
        super();
    }
}