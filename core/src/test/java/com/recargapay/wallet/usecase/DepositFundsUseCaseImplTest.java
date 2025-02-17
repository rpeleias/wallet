package com.recargapay.wallet.usecase;

import com.recargapay.wallet.domain.Transaction;
import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.exception.WalletNotFoundException;
import com.recargapay.wallet.port.in.command.DepositFundsCommand;
import com.recargapay.wallet.port.out.FundsDeposited;
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

class DepositFundsUseCaseImplTest {

    @Mock
    private WalletRepositoryPort walletRepositoryPort;

    private DepositFundsUseCaseImpl depositFundsUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        depositFundsUseCase = new DepositFundsUseCaseImpl(walletRepositoryPort);
    }

    @Test
    void whenWalletIsFoundThenDepositIsSucceeded() {
        long userId = 1L;
        Wallet wallet = Wallet.of(userId);
        BigDecimal initialDepositedValue = BigDecimal.valueOf(50.0f);
        wallet.add(Transaction.fromDeposit(wallet.getId(), initialDepositedValue));
        DepositFundsCommand command = DepositFundsCommand.of(wallet.getId(), 50.0f);

        // when
        when(walletRepositoryPort.findById(wallet.getId())).thenReturn(Optional.of(wallet));

        FundsDeposited deposit = depositFundsUseCase.deposit(command);

        // then
        assertThat(deposit, is(notNullValue()));
        assertThat(deposit.getWalletId(), is(wallet.getId()));
        assertThat(deposit.getUserId(), is(wallet.getUserId()));
        assertThat(deposit.getAmount(), is("USD 100,00"));

        verify(walletRepositoryPort, times(1)).findById(wallet.getId());
        verify(walletRepositoryPort, times(1)).saveOrUpdate(wallet);
    }

    @Test
    void whenWalletNotFoundThenDepositWasNotMade() {
        long walletId = 999L;
        DepositFundsCommand command = DepositFundsCommand.of(walletId, 50.0f);
        when(walletRepositoryPort.findById(command.getWalletId())).thenReturn(Optional.empty());

        // when
        WalletNotFoundException exception = assertThrows(WalletNotFoundException.class, () -> {
            depositFundsUseCase.deposit(command);
        });

        //then
        assertThat(exception.getMessage(), containsString("Wallet with walletId 999 not exists!"));

        verify(walletRepositoryPort, times(1)).findById(command.getWalletId());
        verify(walletRepositoryPort, never()).saveOrUpdate(any());
    }
}