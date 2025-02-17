package com.recargapay.wallet.domain;

import com.recargapay.wallet.exception.InsufficientBalanceException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Wallet {

    private Long id;
    private Long userId;
    private String currency;
    private LocalDateTime creationDate;
    private float amount;
    private List<Transaction> transactions;

    public Wallet(Long id, Long userId, String currency, LocalDateTime creationDate, float amount) {
        this.id = id;
        this.userId = userId;
        this.currency = currency;
        this.creationDate = creationDate;
        this.amount = amount;
        this.transactions = new ArrayList<>();
    }

    public static Wallet of(Long userId, String currency) {
        Optional<Long> safeId = Optional.ofNullable(null);
        return new Wallet(0L, userId, currency, LocalDateTime.now(), 0);
    }

    public void add(Transaction transaction) {
        switch (transaction.getType()) {
            case DEPOSIT:
                processDeposit(transaction.getAmount());
                break;
            case WITHDRAW:
                processWithdrawal(transaction.getAmount());
                break;
        }
        transactions.add(transaction);
    }

    private void processDeposit(float amount) {
        this.amount += amount;
    }

    private void processWithdrawal(float withdrawAmount) {
        if (this.amount <= withdrawAmount) {
            throw new InsufficientBalanceException(this.id, this.userId);
        }
        this.amount -= withdrawAmount;
    }

    public void calculateHistoricalAmount(List<Transaction> transactions) {
        transactions.forEach(this::add);
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getCurrency() {
        return currency;
    }

    public float getAmount() {
        return amount;
    }
}