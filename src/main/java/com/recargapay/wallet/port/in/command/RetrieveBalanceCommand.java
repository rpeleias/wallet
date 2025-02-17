package com.recargapay.wallet.port.in.command;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class RetrieveBalanceCommand {

    @NotNull
    @NotEmpty
    private final Long walletId;


    public RetrieveBalanceCommand(Long walletId) {
        this.walletId = walletId;
    }

    public static RetrieveBalanceCommand of(Long walletId) {
        return new RetrieveBalanceCommand(walletId);
    }

    public Long getWalletId() {
        return walletId;
    }
}
