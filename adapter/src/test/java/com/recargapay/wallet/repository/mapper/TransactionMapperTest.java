package com.recargapay.wallet.repository.mapper;

import com.recargapay.wallet.domain.Transaction;
import com.recargapay.wallet.domain.TransactionType;
import com.recargapay.wallet.repository.entity.TransactionEntity;
import com.recargapay.wallet.repository.entity.WalletEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;


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

        assertThat(transaction.getId(), is(id));
        assertThat(transaction.getWalletId(), is(walletId));
        assertThat(transaction.getType(), is(type));
        assertThat(transaction.getAmount(), is(amount));
        assertThat(transaction.getTransactionDate(), is(transactionDate));
    }

    @Test
    void whenTransactionIsInformedThenTransactionEntityShouldBeReturned() {
        Long id = 1L;
        Long walletId = 100L;
        TransactionType type = TransactionType.DEPOSIT;
        float amount = 500.0f;
        LocalDateTime transactionDate = LocalDateTime.now();

        Transaction transaction = Transaction.of(id, walletId, type, amount, transactionDate);

        TransactionEntity transactionEntity = TransactionMapper.from(transaction);

        assertThat(transactionEntity.getId(), is(nullValue()));
        assertThat(transactionEntity.getWallet().getId(), is(walletId));
        assertThat(transactionEntity.getType(), is(type));
        assertThat(transactionEntity.getAmount(), is(amount));
        assertThat(transactionEntity.getTransactionDate(), is(transactionDate));
    }
}