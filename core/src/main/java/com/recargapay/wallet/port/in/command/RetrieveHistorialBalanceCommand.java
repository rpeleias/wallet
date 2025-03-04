package com.recargapay.wallet.port.in.command;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;

public class RetrieveHistorialBalanceCommand {

    @NotNull
    @NotEmpty
    private Long walletId;

    @NotNull
    @PastOrPresent
    private LocalDateTime from;

    @NotNull
    @PastOrPresent
    private LocalDateTime to;

    public RetrieveHistorialBalanceCommand() {}

    private RetrieveHistorialBalanceCommand(Long walletId, LocalDateTime from, LocalDateTime to) {
        this.walletId = walletId;
        this.from = from;
        this.to = to;
    }

    public static RetrieveHistorialBalanceCommand of(Long walletId, LocalDateTime from, LocalDateTime to) {
        return new RetrieveHistorialBalanceCommand(walletId, from, to);
    }

    public Long getWalletId() {
        return walletId;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }
}
