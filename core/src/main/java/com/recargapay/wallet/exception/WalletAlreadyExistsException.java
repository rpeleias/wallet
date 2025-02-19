package com.recargapay.wallet.exception;

public class WalletAlreadyExistsException extends BusinessException {

    public WalletAlreadyExistsException(Long userId) {
        super(String.format("Wallet with userId %s already exists!", userId));
    }
}
