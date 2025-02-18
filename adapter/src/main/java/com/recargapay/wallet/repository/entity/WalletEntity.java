package com.recargapay.wallet.repository.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "WALLET")
public class WalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "CREATION_DATE", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "AMOUNT", nullable = false)
    private float amount;

    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "WALLET_ID")
    private List<TransactionEntity> transactions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public List<TransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionEntity> transactions) {
        this.transactions = transactions;
    }

    public static class WalletEntityBuilder {

        private Long id;
        private Long userId;
        private LocalDateTime creationDate;
        private float amount;
        private List<TransactionEntity> transactions;

        public WalletEntityBuilder() {
        }

        public WalletEntityBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public WalletEntityBuilder withUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public WalletEntityBuilder withCreationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public WalletEntityBuilder withAmount(float amount) {
            this.amount = amount;
            return this;
        }

        public WalletEntityBuilder withTransactions(List<TransactionEntity> transactions) {
            this.transactions = transactions;
            return this;
        }

        public WalletEntity build() {
            WalletEntity walletEntity = new WalletEntity();
            walletEntity.setId(this.id);
            walletEntity.setUserId(this.userId);
            walletEntity.setCreationDate(this.creationDate);
            walletEntity.setAmount(this.amount);
            walletEntity.setTransactions(this.transactions);
            return walletEntity;
        }
    }
}
