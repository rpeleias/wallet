package com.recargapay.wallet.repository.service;

import com.recargapay.wallet.domain.Transaction;
import com.recargapay.wallet.repository.entity.TransactionEntity;
import com.recargapay.wallet.repository.impl.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TransactionServicePortImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    private TransactionServicePortImpl transactionService;

    private static TransactionEntity createTransactionEntity(Long walletId) {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(1L);
        transactionEntity.setWalletId(walletId);
        transactionEntity.setTransactionDate(LocalDateTime.now());
        transactionEntity.setAmount(100.0f);
        return transactionEntity;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionService = new TransactionServicePortImpl(transactionRepository);
    }

    @Test
    void whenWalletIdIsInformedThenReturnTransactionsBetweenDates() {
        Long walletId = 1L;
        LocalDateTime from = LocalDateTime.now().minusDays(7);
        LocalDateTime to = LocalDateTime.now();

        TransactionEntity transactionEntity = createTransactionEntity(walletId);

        when(transactionRepository.findByWalletIdAndTransactionDateBetween(walletId, from, to)).thenReturn(List.of(transactionEntity));

        List<Transaction> transaction = transactionService.findByWalletIdBetweenDates(walletId, from, to);

        assertThat(transaction, is(not(empty())));
        assertThat(transaction, hasSize(1));
        assertThat(transaction.get(0).getWalletId(), is(walletId));

        verify(transactionRepository, times(1)).findByWalletIdAndTransactionDateBetween(walletId, from, to);
    }

    @Test
    void whenWalletIdIsInformedThenReturnEmptyTransactionsBetweenDates() {
        Long walletId = 1L;
        LocalDateTime from = LocalDateTime.now().minusDays(7);
        LocalDateTime to = LocalDateTime.now();

        when(transactionRepository.findByWalletIdAndTransactionDateBetween(walletId, from, to)).thenReturn(Collections.emptyList());

        List<Transaction> result = transactionService.findByWalletIdBetweenDates(walletId, from, to);

        assertThat(result, is(empty()));
        verify(transactionRepository, times(1)).findByWalletIdAndTransactionDateBetween(walletId, from, to);
    }
}