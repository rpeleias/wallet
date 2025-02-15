package com.recargapay.wallet.domain;

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
        return new Wallet(safeId.orElse(0L), userId, currency, LocalDateTime.now(), 0);
    }

    public void add(Transaction transaction) {
        transactions.add(transaction);
        if (transaction.getType().equals(TransactionType.DEPOSIT)) {
            this.amount += transaction.getAmount();
        }
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