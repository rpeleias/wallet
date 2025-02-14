package com.recargapay.wallet.port.in;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class WalletCreationCommand {

    @NotNull
    @NotEmpty
    public Long userId;

    @NotNull
    @NotEmpty
    public String currency;

    public WalletCreationCommand(Long userId, String currency) {
        this.userId = userId;
        this.currency = currency;


    }
}
