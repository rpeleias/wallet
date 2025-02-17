package com.recargapay.wallet.usecase;

import com.recargapay.wallet.domain.Transaction;
import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.exception.InsufficientBalanceException;
import com.recargapay.wallet.exception.WalletNotFoundException;
import com.recargapay.wallet.port.in.command.TransferFundsCommand;
import com.recargapay.wallet.port.out.FundsTransfered;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TransferFundsUseCaseTest {

    @Mock
    private WalletRepositoryPort walletRepositoryPort;

    private TransferFundsUseCaseImpl transferFundsUseCase;

    private static Wallet createWallet(long userId, float value) {
        Wallet fromWallet = Wallet.of(userId);
        BigDecimal fromDepositedValue = BigDecimal.valueOf(value);
        fromWallet.add(Transaction.fromDeposit(fromWallet.getId(), fromDepositedValue));
        return fromWallet;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transferFundsUseCase = new TransferFundsUseCaseImpl(walletRepositoryPort);
    }

    @Test
    void whenWalletFromAndToIsFoundThenTransferenceIsSucceeded() {
        Wallet fromWallet = createWallet(1L, 400.0f);
        Wallet toWallet = createWallet(2L, 100.0f);

        TransferFundsCommand command = TransferFundsCommand.of(fromWallet.getId(), toWallet.getId(), BigDecimal.valueOf(150.0f));

        // when
        when(walletRepositoryPort.findById(any())).thenReturn(Optional.of(fromWallet)).thenReturn(Optional.of(toWallet));

        FundsTransfered fundsTransferred = transferFundsUseCase.transfer(command);

        // then
        assertThat(fundsTransferred, is(notNullValue()));
        assertThat(fundsTransferred.getFromWalletId(), is(fromWallet.getId()));
        assertThat(fundsTransferred.getToWalletId(), is(toWallet.getId()));
        assertThat(fundsTransferred.getTransferredAmount(), is("USD 150,00"));
        assertThat(fundsTransferred.getFromWalletNewBalance(), is("USD 250,00"));
        assertThat(fundsTransferred.getToWalletNewBalance(), is("USD 250,00"));

        verify(walletRepositoryPort, times(2)).findById(any());
    }

    @Test
    void whenWalletIsNotFoundThenWalletNotFoundExceptionIsThrown() {
        Wallet fromWallet = createWallet(1L, 400.0f);
        Wallet toWallet = createWallet(2L, 100.0f);

        TransferFundsCommand command = TransferFundsCommand.of(fromWallet.getId(), toWallet.getId(), BigDecimal.valueOf(150.0f));

        // when
        when(walletRepositoryPort.findById(any())).thenReturn(Optional.empty()).thenReturn(Optional.of(toWallet));

        WalletNotFoundException exception = assertThrows(WalletNotFoundException.class, () -> {
            transferFundsUseCase.transfer(command);
        });

        //then
        assertThat(exception.getMessage(), containsString("Wallet with walletId 0 not exists!"));

        verify(walletRepositoryPort, times(1)).findById(any());
    }

    @Test
    void whenWalletIsFoundAndBalanceIsInsufficientThenInsufficientBalanceIsThrown() {
        Wallet fromWallet = createWallet(1L, 400.0f);
        Wallet toWallet = createWallet(2L, 100.0f);

        TransferFundsCommand command = TransferFundsCommand.of(fromWallet.getId(), toWallet.getId(), BigDecimal.valueOf(500.0f));

        // when
        when(walletRepositoryPort.findById(any())).thenReturn(Optional.of(fromWallet)).thenReturn(Optional.of(toWallet));

        InsufficientBalanceException exception = assertThrows(InsufficientBalanceException.class, () -> {
            transferFundsUseCase.transfer(command);
        });

        // then
        assertThat(exception.getMessage(), is("Wallet with id 0 from userId 1 with insufficient balance!")); // Amount should not change/ Am

        verify(walletRepositoryPort, times(2)).findById(any());

    }
}
