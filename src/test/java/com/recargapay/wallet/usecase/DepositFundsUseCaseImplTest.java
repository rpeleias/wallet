package com.recargapay.wallet.usecase;

import com.recargapay.wallet.domain.Transaction;
import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.exception.WalletNotFoundException;
import com.recargapay.wallet.port.in.command.DepositFundsCommand;
import com.recargapay.wallet.port.out.FundsDeposited;
import com.recargapay.wallet.port.out.WalletRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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
import static org.mockito.Mockito.mock;
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
    void testDepositFundsSuccess() {
        // Arrange
        Wallet wallet = Wallet.of(1L, "USD");
        BigDecimal initialDepositedValue = BigDecimal.valueOf(50.0f);
        wallet.add(Transaction.ofDeposity(wallet.getId(), initialDepositedValue)); // Initial deposit
        DepositFundsCommand command = DepositFundsCommand.of(wallet.getId(), 50.0f);
        when(walletRepositoryPort.findById(wallet.getId())).thenReturn(Optional.of(wallet));

        // Act
        FundsDeposited result = depositFundsUseCase.deposit(command);

        // Assert
        assertThat(result, is(notNullValue()));
        assertThat(result.getWalletId(), is(wallet.getId()));
        assertThat(result.getUserId(), is(wallet.getUserId()));
        assertThat(result.getAmount(), is("USD 100,00"));

        // Verify interactions
        verify(walletRepositoryPort, times(1)).findById(wallet.getId());
        verify(walletRepositoryPort, times(1)).saveOrUpdate(wallet);
    }

    @Test
    void testDepositFundsWalletNotFound() {
        // Arrange
        DepositFundsCommand command = DepositFundsCommand.of(999L, 50.0f);
        when(walletRepositoryPort.findById(command.getWalletId())).thenReturn(Optional.empty());

        // Act & Assert
        WalletNotFoundException exception = assertThrows(WalletNotFoundException.class, () -> {
            depositFundsUseCase.deposit(command);
        });

        assertThat(exception.getMessage(), containsString("Wallet with walletId 999 not exists!"));

        // Verify interactions
        verify(walletRepositoryPort, times(1)).findById(command.getWalletId());
        verify(walletRepositoryPort, never()).saveOrUpdate(any());
    }

    @Test
    void testDepositFundsSaveOrUpdateCalledWithCorrectWallet() {
        // Arrange
        Wallet wallet = Wallet.of(1L, "USD");
        DepositFundsCommand command = DepositFundsCommand.of(wallet.getId(), 50.0f);
        when(walletRepositoryPort.findById(wallet.getId())).thenReturn(Optional.of(wallet));

        // Act
        depositFundsUseCase.deposit(command);

        // Capture the wallet passed to saveOrUpdate
        ArgumentCaptor<Wallet> walletCaptor = ArgumentCaptor.forClass(Wallet.class);
        verify(walletRepositoryPort).saveOrUpdate(walletCaptor.capture());

        Wallet capturedWallet = walletCaptor.getValue();
        assertThat(capturedWallet.getId(), is(wallet.getId()));
        assertThat(capturedWallet.getAmount(), is(50.0f)); // Amount after deposit
    }
}