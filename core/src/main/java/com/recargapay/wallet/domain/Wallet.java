package com.recargapay.wallet.domain;

import com.recargapay.wallet.exception.InsufficientBalanceException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Wallet {

    private final Long id;
    private final Long userId;
    private final LocalDateTime creationDate;
    private final List<Transaction> transactions;
    private float amount;

    public Wallet(Long id, Long userId, LocalDateTime creationDate, float amount) {
        this(id, userId, creationDate, amount, Collections.emptyList());
    }

    public Wallet(Long id, Long userId, LocalDateTime creationDate, float amount, List<Transaction> transactions) {
        this.id = id;
        this.userId = userId;
        this.creationDate = creationDate;
        this.amount = amount;
        this.transactions = transactions;
    }

    public static Wallet of(Long userId) {
        return Wallet.of(userId, LocalDateTime.now(), 0);
    }

    public static Wallet of(Long userId, LocalDateTime creationDate, float amount) {
        return Wallet.of(null, userId, creationDate, amount);
    }

    public static Wallet of(Long id, Long userId, LocalDateTime creationDate, float amount) {
        return new Wallet(id, userId, creationDate, amount);
    }

    public static Wallet of(Long id, Long userId, LocalDateTime creationDate, float amount, List<Transaction> transactions) {
        return new Wallet(id, userId, creationDate, amount, transactions);
    }

    public void add(Transaction transaction) {
        switch (transaction.getType()) {
            case DEPOSIT, TRANSFER_IN:
                processDeposit(transaction.getAmount());
                break;
            case WITHDRAW, TRANSFER_OUT:
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
        this.amount = 0;
        transactions.forEach(this::add);
    }

    public void transfer(Wallet toWallet, float transferValue) {
        Transaction fromTransaction = Transaction.fromTransferOut(this.getId(), BigDecimal.valueOf(transferValue));
        Transaction toTransaction = Transaction.fromTransferIn(toWallet.getId(), BigDecimal.valueOf(transferValue));

        this.add(fromTransaction);
        toWallet.add(toTransaction);
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public float getAmount() {
        return amount;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}