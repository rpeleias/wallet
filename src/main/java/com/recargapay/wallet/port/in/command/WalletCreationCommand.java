package com.recargapay.wallet.port.in.command;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class WalletCreationCommand {

    @NotNull
    @NotEmpty
    private Long userId;

    @NotNull
    @NotEmpty
    private String currency;

    public WalletCreationCommand(Long userId, String currency) {
        this.userId = userId;
        this.currency = currency;
    }

    public Long getUserId() {
        return userId;
    }

    public String getCurrency() {
        return currency;
    }
}
