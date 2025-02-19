package com.recargapay.wallet.port.in.command;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TransferFundsCommand {

    @NotNull
    private Long fromWalletId;

    @NotNull
    private Long toWalletId;

    @NotNull
    @NotEmpty
    private BigDecimal value;

    public TransferFundsCommand() {}

    public TransferFundsCommand(Long fromWalletId, Long toWalletId, BigDecimal value) {
        this.fromWalletId = fromWalletId;
        this.toWalletId = toWalletId;
        this.value = value;
    }

    public static TransferFundsCommand of(Long fromWalletId, Long toWalletId, BigDecimal value) {
        return new TransferFundsCommand(fromWalletId, toWalletId, value);
    }

    public Long getFromWalletId() {
        return fromWalletId;
    }

    public Long getToWalletId() {
        return toWalletId;
    }

    public BigDecimal getValue() {
        return value;
    }
}
