package com.gsm.jupjup.advice.exception;

public class EquipmentAllowAmountZeroException extends RuntimeException{
    public EquipmentAllowAmountZeroException(String msg, Throwable t) {
        super(msg, t);
    }

    public EquipmentAllowAmountZeroException(String msg) {
        super(msg);
    }

    public EquipmentAllowAmountZeroException() {
        super();
    }
}
