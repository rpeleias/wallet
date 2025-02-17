package com.recargapay.wallet.usecase;

import com.recargapay.wallet.domain.Transaction;
import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.exception.InsufficientBalanceException;
import com.recargapay.wallet.exception.WalletNotFoundException;
import com.recargapay.wallet.port.in.command.RetrieveHistorialBalanceCommand;
import com.recargapay.wallet.port.out.HistorialBalanceRetrieved;
import com.recargapay.wallet.port.out.repository.TransactionRepositoryPort;
import com.recargapay.wallet.port.out.repository.WalletRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RetrieveHistoricalBalanceUseCaseTest {

    private final LocalDateTime LAST_THREE_MONTHS = LocalDateTime.now().minusMonths(3);
    private final LocalDateTime LAST_MONTH = LocalDateTime.now().minusMonths(1);

    @Mock
    private WalletRepositoryPort walletRepositoryPort;

    @Mock
    private TransactionRepositoryPort transactionRepositoryPort;

    private RetrieveHistoricalBalanceUseCaseImpl retrieveHistoricalBalanceUseCase;

    private static Wallet createWallet() {
        long userId = 1L;
        Wallet wallet = Wallet.of(userId, "USD");
        return wallet;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        retrieveHistoricalBalanceUseCase = new RetrieveHistoricalBalanceUseCaseImpl(walletRepositoryPort, transactionRepositoryPort);
    }

    @Test
    void whenValidTransactionDateIsInformedThenHistoricalBalanceIsRetrieved() {
        Wallet wallet = createWallet();
        long walletId = wallet.getId();


        List<Transaction> transactions = Arrays.asList(
                Transaction.fromDeposit(walletId, BigDecimal.valueOf(200)),
                Transaction.fromDeposit(walletId, BigDecimal.valueOf(200)),
                Transaction.fromWithdraw(walletId, BigDecimal.valueOf(150))
        );

        when(walletRepositoryPort.findById(walletId)).thenReturn(Optional.of(wallet));
        when(transactionRepositoryPort.findByWalletIdBetweenDates(walletId, LAST_THREE_MONTHS, LAST_MONTH)).thenReturn(transactions);

        RetrieveHistorialBalanceCommand command = RetrieveHistorialBalanceCommand.of(walletId, LAST_THREE_MONTHS, LAST_MONTH);
        HistorialBalanceRetrieved balanceRetrieved = retrieveHistoricalBalanceUseCase.retrieve(command);

        assertThat(balanceRetrieved.getId(), is(walletId));
        assertThat(balanceRetrieved.getTransactions(), hasSize(3));
        assertThat(balanceRetrieved.getAmount(), is("USD 250,00"));
        verify(walletRepositoryPort).findById(walletId);
        verify(transactionRepositoryPort).findByWalletIdBetweenDates(walletId, LAST_THREE_MONTHS, LAST_MONTH);
    }

    @Test
    void whenInvalidTransactionDateIsInformedThenEmptyBalanceIsRetrieved() {
        Wallet wallet = createWallet();
        long walletId = wallet.getId();

        List<Transaction> transactions = List.of();

        when(walletRepositoryPort.findById(walletId)).thenReturn(Optional.of(wallet));
        when(transactionRepositoryPort.findByWalletIdBetweenDates(walletId, LAST_THREE_MONTHS, LAST_MONTH)).thenReturn(transactions);

        RetrieveHistorialBalanceCommand command = RetrieveHistorialBalanceCommand.of(walletId, LAST_THREE_MONTHS, LAST_MONTH);
        HistorialBalanceRetrieved balanceRetrieved = retrieveHistoricalBalanceUseCase.retrieve(command);

        assertThat(balanceRetrieved.getId(), is(walletId));
        assertThat(balanceRetrieved.getTransactions(), hasSize(0));
        assertThat(balanceRetrieved.getAmount(), is("USD 0,00"));
        verify(walletRepositoryPort).findById(walletId);
        verify(transactionRepositoryPort).findByWalletIdBetweenDates(walletId, LAST_THREE_MONTHS, LAST_MONTH);
    }

    @Test
    void whenInvalidTransactionsAreFoundThenInsuficientBalanceExceptionIsThrown() {
        Wallet wallet = createWallet();
        long walletId = wallet.getId();


        List<Transaction> transactions = Arrays.asList(
                Transaction.fromDeposit(walletId, BigDecimal.valueOf(200)),
                Transaction.fromDeposit(walletId, BigDecimal.valueOf(200)),
                Transaction.fromWithdraw(walletId, BigDecimal.valueOf(600))
        );

        when(walletRepositoryPort.findById(walletId)).thenReturn(Optional.of(wallet));
        when(transactionRepositoryPort.findByWalletIdBetweenDates(walletId, LAST_THREE_MONTHS, LAST_MONTH)).thenReturn(transactions);

        RetrieveHistorialBalanceCommand command = RetrieveHistorialBalanceCommand.of(walletId, LAST_THREE_MONTHS, LAST_MONTH);

        InsufficientBalanceException exception = assertThrows(InsufficientBalanceException.class, () -> {
            retrieveHistoricalBalanceUseCase.retrieve(command);
        });

        // then
        assertThat(exception.getMessage(), is("Wallet with id 0 from userId 1 with insufficient balance!"));

        verify(walletRepositoryPort).findById(walletId);
        verify(transactionRepositoryPort).findByWalletIdBetweenDates(walletId, LAST_THREE_MONTHS, LAST_MONTH);
    }


    @Test
    void whenWalletIsNotFoundThenWalletNotFoundExceptionIsthrown() {
        long invalidWalletId = 999L;

        when(walletRepositoryPort.findById(invalidWalletId)).thenReturn(Optional.empty());

        RetrieveHistorialBalanceCommand command = RetrieveHistorialBalanceCommand.of(invalidWalletId, LAST_THREE_MONTHS, LAST_MONTH);

        WalletNotFoundException exception = assertThrows(WalletNotFoundException.class, () -> retrieveHistoricalBalanceUseCase.retrieve(command));

        assertThat(exception.getMessage(), containsString("Wallet with walletId 999 not exists!"));
        verify(walletRepositoryPort).findById(invalidWalletId);
        verify(transactionRepositoryPort, never()).findByWalletIdBetweenDates(any(), any(), any());
    }
}