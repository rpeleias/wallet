package com.recargapay.wallet.exception;

public class InsufficientBalanceException extends BusinessException {

    public InsufficientBalanceException(Long id, Long userId) {
        super(String.format("Wallet with id %s from userId %s with insufficient balance!", id, userId), 400);
    }
}
