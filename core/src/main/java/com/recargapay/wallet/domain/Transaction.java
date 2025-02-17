package com.recargapay.wallet.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

    private Long walletId;
    private TransactionType type;
    private float amount;
    private LocalDateTime transactionDate;

    private Transaction(Long walletId, TransactionType type, float amount, LocalDateTime transactionDate) {
        this.walletId = walletId;
        this.type = type;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public static Transaction fromDeposit(Long walletId, BigDecimal value) {
        return new Transaction(
                walletId,
                TransactionType.DEPOSIT,
                value.floatValue(),
                LocalDateTime.now()
        );
    }

    public static Transaction fromWithdraw(Long walletId, BigDecimal value) {
        return new Transaction(
                walletId,
                TransactionType.WITHDRAW,
                value.floatValue(),
                LocalDateTime.now()
        );
    }

    public static Transaction fromTransferIn(Long walletId, BigDecimal value) {
        return new Transaction(
                walletId,
                TransactionType.TRANSFER_IN,
                value.floatValue(),
                LocalDateTime.now()
        );
    }
    public static Transaction fromTransferOut(Long walletId, BigDecimal value) {
        return new Transaction(
                walletId,
                TransactionType.TRANSFER_OUT,
                value.floatValue(),
                LocalDateTime.now()
        );
    }



    public TransactionType getType() {
        return type;
    }

    public float getAmount() {
        return amount;
    }
}
