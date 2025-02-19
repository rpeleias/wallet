package com.recargapay.wallet.repository.mapper;

import com.recargapay.wallet.domain.Transaction;
import com.recargapay.wallet.domain.TransactionType;
import com.recargapay.wallet.repository.entity.TransactionEntity;
import com.recargapay.wallet.repository.entity.WalletEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionMapperTest {

    private static TransactionEntity createTransactionEntity(Long id, Long walletId, TransactionType type, float amount, LocalDateTime transactionDate) {
        WalletEntity walletEntity = new WalletEntity(walletId);

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(id);

        transactionEntity.setWallet(walletEntity);
        transactionEntity.setType(type);
        transactionEntity.setAmount(amount);
        transactionEntity.setTransactionDate(transactionDate);
        return transactionEntity;
    }

    @Test
    void whenTransactionEntityIsInformedThenTransactionShouldBeReturned() {
        Long id = 1L;
        Long walletId = 100L;
        TransactionType type = TransactionType.DEPOSIT;
        float amount = 500.0f;
        LocalDateTime transactionDate = LocalDateTime.now();

        TransactionEntity transactionEntity = createTransactionEntity(id, walletId, type, amount, transactionDate);

        Transaction transaction = TransactionMapper.from(transactionEntity);

        assertThat(transaction).isNotNull();
        assertThat(transaction.getId()).isEqualTo(id);
        assertThat(transaction.getWalletId()).isEqualTo(walletId);
        assertThat(transaction.getType()).isEqualTo(type);
        assertThat(transaction.getAmount()).isEqualByComparingTo(amount);
        assertThat(transaction.getTransactionDate()).isEqualTo(transactionDate);
    }
}