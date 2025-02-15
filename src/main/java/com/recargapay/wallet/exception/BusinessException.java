package com.recargapay.wallet.exception;

public class BusinessException extends  RuntimeException {

    protected int errorCode;

    public BusinessException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
