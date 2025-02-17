package com.recargapay.wallet.usecase;

import com.recargapay.wallet.domain.Transaction;
import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.exception.InsufficientBalanceException;
import com.recargapay.wallet.exception.WalletNotFoundException;
import com.recargapay.wallet.port.in.command.WithdrawFundsCommand;
import com.recargapay.wallet.port.out.FundsWithdrawed;
import com.recargapay.wallet.port.out.repository.WalletRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WithdrawFundsUseCaseImplTest {

    @Mock
    private WalletRepositoryPort walletRepositoryPort;

    private WithdrawFundsUseCaseImpl withdrawFundsUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        withdrawFundsUseCase = new WithdrawFundsUseCaseImpl(walletRepositoryPort);
    }

    @Test
    void whenWalletIsFoundThenWithdrawIsSucceeded() {
        long userId = 1L;
        Wallet wallet = Wallet.of(userId, "USD");
        BigDecimal initialDepositedValue = BigDecimal.valueOf(50.0f);
        wallet.add(Transaction.fromDeposit(wallet.getId(), initialDepositedValue));

        WithdrawFundsCommand command = WithdrawFundsCommand.of(wallet.getId(), 30.0f);

        // when
        when(walletRepositoryPort.findById(wallet.getId())).thenReturn(Optional.of(wallet));

        FundsWithdrawed deposit = withdrawFundsUseCase.withdraw(command);

        // then
        assertThat(deposit, is(notNullValue()));
        assertThat(deposit.getWalletId(), is(wallet.getId()));
        assertThat(deposit.getUserId(), is(wallet.getUserId()));
        assertThat(deposit.getAmount(), is("USD 20,00"));

        verify(walletRepositoryPort, times(1)).findById(wallet.getId());
        verify(walletRepositoryPort, times(1)).saveOrUpdate(wallet);
    }

    @Test
    void whenWalletIsFoundThenWithdrawWithInsufficientBalanceException() {
        long userId = 1L;
        Wallet wallet = Wallet.of(userId, "USD");
        BigDecimal initialDepositedValue = BigDecimal.valueOf(50.0f);
        wallet.add(Transaction.fromDeposit(wallet.getId(), initialDepositedValue));

        WithdrawFundsCommand command = WithdrawFundsCommand.of(wallet.getId(), 80.0f);

        // when
        when(walletRepositoryPort.findById(wallet.getId())).thenReturn(Optional.of(wallet));

        InsufficientBalanceException exception = assertThrows(InsufficientBalanceException.class, () -> {
            withdrawFundsUseCase.withdraw(command);
        });

        // then
        assertThat(exception.getMessage(), is("Wallet with id 0 from userId 1 with insufficient balance!")); // Amount should not change/ Am

        verify(walletRepositoryPort, times(1)).findById(wallet.getId());
        verify(walletRepositoryPort, times(0)).saveOrUpdate(wallet);
    }

    @Test
    void whenWalletNotFoundThenDepositWasNotMade() {
        long walletId = 999L;
        WithdrawFundsCommand command = WithdrawFundsCommand.of(walletId, 50.0f);
        when(walletRepositoryPort.findById(command.getWalletId())).thenReturn(Optional.empty());

        // when
        WalletNotFoundException exception = assertThrows(WalletNotFoundException.class, () -> {
            withdrawFundsUseCase.withdraw(command);
        });

        //then
        assertThat(exception.getMessage(), containsString("Wallet with walletId 999 not exists!"));

        verify(walletRepositoryPort, times(1)).findById(command.getWalletId());
        verify(walletRepositoryPort, never()).saveOrUpdate(any());
    }
}