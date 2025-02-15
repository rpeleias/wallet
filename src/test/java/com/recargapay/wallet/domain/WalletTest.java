package com.recargapay.wallet.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WalletTest {

    private Wallet wallet;

    @BeforeEach
    void setUp() {
        wallet = Wallet.of(1L, "USD");
    }

    @Test
    void whenAttributesIsInformedThenAWalletIsCreated() {
        assertThat(wallet.getUserId(), is(1L));
        assertThat(wallet.getCurrency(), is("USD"));
        assertThat(wallet.getAmount(), is(0.0f));
        assertThat(wallet.getId(), is(0L));
    }

    @Test
    void whenDepositIsMadeThenAmountIsUpdated() {
        Transaction transaction = mock(Transaction.class);
        when(transaction.getType()).thenReturn(TransactionType.DEPOSIT);
        when(transaction.getAmount()).thenReturn(100.0f);

        wallet.add(transaction);

        assertThat(wallet.getAmount(), is(100.0f));
        verify(transaction, times(1)).getType();
        verify(transaction, times(1)).getAmount();
    }

    @Test
    void testAddNonDepositTransaction() {
        // Mock a Transaction
        Transaction transaction = mock(Transaction.class);
        when(transaction.getType()).thenReturn(TransactionType.WITHDRAW);
        when(transaction.getAmount()).thenReturn(50.0f);

        wallet.add(transaction);

        assertThat(wallet.getAmount(), is(0.0f)); // Amount should not change
        verify(transaction, times(1)).getType();
        verify(transaction, never()).getAmount(); // Amount is not used for non-deposit transactions
    }

    @Test
    void testWalletWithNullId() {
        Wallet walletWithNullId = Wallet.of(2L, "BRL");
        assertThat(walletWithNullId.getId(), is(0L)); // Default ID as per the `of` method
        assertThat(walletWithNullId.getUserId(), is(2L));
        assertThat(walletWithNullId.getCurrency(), is("BRL"));
    }
}