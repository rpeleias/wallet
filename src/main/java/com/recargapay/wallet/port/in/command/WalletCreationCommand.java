package com.recargapay.wallet.port.in.command;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class WalletCreationCommand {

    @NotNull
    @NotEmpty
    private Long userId;

    public WalletCreationCommand(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
