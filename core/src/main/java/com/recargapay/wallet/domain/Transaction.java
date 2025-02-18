package com.recargapay.wallet.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

    private final Long walletId;
    private final TransactionType type;
    private final float amount;
    private final LocalDateTime transactionDate;
    private final Long id;

    private Transaction(Long walletId, TransactionType type, float amount, LocalDateTime transactionDate) {
        this(0L, walletId, type, amount, transactionDate);
    }

    private Transaction(Long id, Long walletId, TransactionType type, float amount, LocalDateTime transactionDate) {
        this.id = id;
        this.walletId = walletId;
        this.type = type;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public static Transaction of(Long id, Long walletId, TransactionType transactionType, float amount, LocalDateTime transactionDate) {
        return new Transaction(id, walletId, transactionType, amount, transactionDate);
    }

    public static Transaction fromDeposit(Long walletId, BigDecimal value) {
        return new Transaction(walletId, TransactionType.DEPOSIT, value.floatValue(), LocalDateTime.now());
    }

    public static Transaction fromWithdraw(Long walletId, BigDecimal value) {
        return new Transaction(walletId, TransactionType.WITHDRAW, value.floatValue(), LocalDateTime.now());
    }

    public static Transaction fromTransferIn(Long walletId, BigDecimal value) {
        return new Transaction(walletId, TransactionType.TRANSFER_IN, value.floatValue(), LocalDateTime.now());
    }

    public static Transaction fromTransferOut(Long walletId, BigDecimal value) {
        return new Transaction(walletId, TransactionType.TRANSFER_OUT, value.floatValue(), LocalDateTime.now());
    }


    public TransactionType getType() {
        return type;
    }

    public float getAmount() {
        return amount;
    }

    public Long getWalletId() {
        return walletId;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public Long getId() {
        return id;
    }
}
