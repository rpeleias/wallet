package com.recargapay.wallet.usecase;

import com.recargapay.wallet.domain.Transaction;
import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.exception.WalletNotFoundException;
import com.recargapay.wallet.port.in.command.RetrieveBalanceCommand;
import com.recargapay.wallet.port.outh.BalanceRetrieved;
import com.recargapay.wallet.port.outh.repository.WalletRepositoryPort;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RetrieveBalanceUseCaseImplTest {

    @Mock
    private WalletRepositoryPort walletRepositoryPort;

    private RetrieveBalanceUseCaseImpl retrieveBalanceUseCase;

    private static Wallet createWallet() {
        long userId = 1L;
        Wallet wallet = Wallet.of(userId);
        BigDecimal initialDepositedValue = BigDecimal.valueOf(100.0f);
        wallet.add(Transaction.fromDeposit(wallet.getId(), initialDepositedValue));
        return wallet;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        retrieveBalanceUseCase = new RetrieveBalanceUseCaseImpl(walletRepositoryPort);
    }

    @Test
    void whenWalletIsFoundThenBalanceRetrievementIsSucceeded() {
        Wallet wallet = createWallet();
        RetrieveBalanceCommand command = RetrieveBalanceCommand.of(wallet.getId());

        // when
        when(walletRepositoryPort.findById(wallet.getId())).thenReturn(Optional.of(wallet));

        BalanceRetrieved balance = retrieveBalanceUseCase.retrieve(command);

        // then
        assertThat(balance, is(notNullValue()));
        assertThat(balance.getId(), is(wallet.getId()));
        assertThat(balance.getAmount(), is("USD 100,00"));

        verify(walletRepositoryPort, times(1)).findById(wallet.getId());
    }

    @Test
    void whenWalletNotFoundThenDepositWasNotMade() {
        long walletId = 999L;
        RetrieveBalanceCommand command = RetrieveBalanceCommand.of(walletId);
        when(walletRepositoryPort.findById(command.getWalletId())).thenReturn(Optional.empty());

        // when
        WalletNotFoundException exception = assertThrows(WalletNotFoundException.class, () -> {
            retrieveBalanceUseCase.retrieve(command);
        });

        //then
        assertThat(exception.getMessage(), containsString("Wallet with walletId 999 not exists!"));

        verify(walletRepositoryPort, times(1)).findById(command.getWalletId());
    }
}