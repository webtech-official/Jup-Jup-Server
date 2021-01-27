package com.gsm.jupjup.advice.exception;

public class EquipmentAllowAmountExceedException extends RuntimeException{
    public EquipmentAllowAmountExceedException(String msg, Throwable t) {
        super(msg, t);
    }

    public EquipmentAllowAmountExceedException(String msg) {
        super(msg);
    }

    public EquipmentAllowAmountExceedException() {
        super();
    }
}
