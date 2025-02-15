package com.recargapay.wallet.exception;

public class WalletNotFoundException extends BusinessException {

    public WalletNotFoundException(Long walletId) {
        super(String.format("Wallet with walletId %s not exists!", walletId), 404);
    }
}
