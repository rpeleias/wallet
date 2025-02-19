package com.recargapay.wallet.exception;

public class BusinessException extends RuntimeException {

    protected int errorCode;

    public BusinessException(String message) {
        super(message);
    }
}
