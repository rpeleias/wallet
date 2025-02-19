package com.recargapay.wallet.domain;

import com.recargapay.wallet.exception.InsufficientBalanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WalletTest {

    private static final long USER_ID = 1L;

    private Wallet wallet;

    @BeforeEach
    void setUp() {
        wallet = Wallet.of(USER_ID);
    }

    @Test
    void whenAttributesIsInformedThenAWalletIsCreated() {
        assertThat(wallet.getUserId(), is(1L));
        assertThat(wallet.getAmount(), is(0.0f));
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

        InsufficientBalanceException exception = assertThrows(InsufficientBalanceException.class, () -> {
            wallet.add(withdraw);
        });

        assertThat(exception.getMessage(), containsString("userId 1 with insufficient balance!")); // Amount should not change/ Am
    }

    @Test
    void whenManyTransactionsAreAddedThenHistoricalAmountIsCalculated() {
        Transaction depositOne = Transaction.fromDeposit(USER_ID, new BigDecimal(100));
        Transaction depositTwo = Transaction.fromDeposit(USER_ID, new BigDecimal(100));
        Transaction withdraw = Transaction.fromWithdraw(USER_ID, new BigDecimal(80));

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(depositOne);
        transactions.add(depositTwo);
        transactions.add(withdraw);

        wallet.calculateHistoricalAmount(transactions);

        assertThat(wallet.getAmount(), is(120.0f)); // Amount should not change
    }

    @Test
    void whenFromTransactionIsInformedThenItShouldBeTransferred() {
        Wallet fromWallet = Wallet.of(2L);
        fromWallet.add(Transaction.fromDeposit(fromWallet.getId(), BigDecimal.valueOf(200.00)));

        Wallet toWallet = Wallet.of(3L);
        toWallet.add(Transaction.fromDeposit(fromWallet.getId(), BigDecimal.valueOf(300.00)));

        fromWallet.transfer(toWallet, 100);

        assertThat(fromWallet.getAmount(), is(100.0f));
        assertThat(toWallet.getAmount(), is(400.0f));
    }

    @Test
    void whenOvervaluedTransactionIsInformedThenInsufficientBalanceExceptionShouldBeThrown() {
        Wallet fromWallet = Wallet.of(2L);
        fromWallet.add(Transaction.fromDeposit(fromWallet.getId(), BigDecimal.valueOf(200.00)));

        Wallet toWallet = Wallet.of(3L);
        toWallet.add(Transaction.fromDeposit(fromWallet.getId(), BigDecimal.valueOf(300.00)));

        InsufficientBalanceException exception = assertThrows(InsufficientBalanceException.class, () -> {
            fromWallet.transfer(toWallet, 500);
        });

        assertThat(exception.getMessage(), containsString("userId 2 with insufficient balance!")); // Amount should not change/ Am

        assertThat(fromWallet.getAmount(), is(200.0f));
        assertThat(toWallet.getAmount(), is(300.0f));
    }
}