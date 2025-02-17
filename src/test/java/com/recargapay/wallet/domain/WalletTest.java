package com.recargapay.wallet.domain;

import com.recargapay.wallet.exception.InsufficientBalanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WalletTest {

    private static final long USER_ID = 1L;

    private Wallet wallet;

    @BeforeEach
    void setUp() {
        wallet = Wallet.of(USER_ID, "USD");
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
        Transaction transaction = Transaction.fromDeposit(USER_ID, new BigDecimal(100));
        wallet.add(transaction);

        assertThat(wallet.getAmount(), is(100.0f));
    }

    @Test
    void whenWithdrawWithPositiveBalanceTransactionThenBalanceIsWitdrawed() {
        Transaction deposit = Transaction.fromDeposit(USER_ID, new BigDecimal(100));
        wallet.add(deposit);

        Transaction withdraw = Transaction.fromWithdraw(USER_ID, new BigDecimal(40));
        wallet.add(withdraw);

        assertThat(wallet.getAmount(), is(60.0f)); // Amount should not change
    }

    @Test
    void whenWithdrawWithInvalidBalanceThenInsufficientBalanceEsceptionIsThrown() {
        Transaction deposit = Transaction.fromDeposit(USER_ID, new BigDecimal(30));
        wallet.add(deposit);

        Transaction withdraw = Transaction.fromWithdraw(USER_ID, new BigDecimal(120));
        wallet.add(withdraw);

        InsufficientBalanceException exception = assertThrows(InsufficientBalanceException.class, () -> {
            wallet.add(withdraw);
        });

        assertThat(exception.getMessage(), is("Wallet with id 0 from userId 1 with insufficient balance!")); // Amount should not change/ Am
    }
}