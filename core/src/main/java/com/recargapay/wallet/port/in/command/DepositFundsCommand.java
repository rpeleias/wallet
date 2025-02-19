package com.recargapay.wallet.port.in.command;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class DepositFundsCommand {

    @NotNull
    @NotEmpty
    private Long walletId;

    @NotNull
    @NotEmpty
    private BigDecimal value;

    public DepositFundsCommand() {}

    private DepositFundsCommand(Long walletId, BigDecimal value) {
        this.walletId = walletId;
        this.value = value;
    }

    public Long getWalletId() {
        return walletId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public static DepositFundsCommand of(Long walletId, float value) {
        return new DepositFundsCommand(walletId, BigDecimal.valueOf(value));
    }
}
