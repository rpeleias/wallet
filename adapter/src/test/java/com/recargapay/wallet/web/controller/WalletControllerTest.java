package com.recargapay.wallet.web.controller;

import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.port.in.command.DepositFundsCommand;
import com.recargapay.wallet.port.in.command.RetrieveBalanceCommand;
import com.recargapay.wallet.port.in.command.RetrieveHistorialBalanceCommand;
import com.recargapay.wallet.port.in.command.TransferFundsCommand;
import com.recargapay.wallet.port.in.command.WalletCreationCommand;
import com.recargapay.wallet.port.in.command.WithdrawFundsCommand;
import com.recargapay.wallet.port.in.usecase.DepositFundsUseCase;
import com.recargapay.wallet.port.in.usecase.RetrieveBalanceUseCase;
import com.recargapay.wallet.port.in.usecase.RetrieveHistorialBalanceUseCase;
import com.recargapay.wallet.port.in.usecase.TransferFundsUseCase;
import com.recargapay.wallet.port.in.usecase.WalletCreationUseCase;
import com.recargapay.wallet.port.in.usecase.WithdrawFundsUseCase;
import com.recargapay.wallet.port.out.BalanceRetrieved;
import com.recargapay.wallet.port.out.CreatedWallet;
import com.recargapay.wallet.port.out.FundsDeposited;
import com.recargapay.wallet.port.out.FundsTransfered;
import com.recargapay.wallet.port.out.FundsWithdrawed;
import com.recargapay.wallet.port.out.HistorialBalanceRetrieved;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WalletControllerTest {

    private WalletCreationUseCase walletCreationUseCase;
    private RetrieveBalanceUseCase retrieveBalanceUseCase;
    private RetrieveHistorialBalanceUseCase retrieveHistorialBalanceUseCase;
    private DepositFundsUseCase depositFundsUseCase;
    private WithdrawFundsUseCase withdrawFundsUseCase;
    private TransferFundsUseCase transferFundsUseCase;

    private WalletController walletController;

    @BeforeEach
    void setUp() {
        walletCreationUseCase = mock(WalletCreationUseCase.class);
        retrieveBalanceUseCase = mock(RetrieveBalanceUseCase.class);
        retrieveHistorialBalanceUseCase = mock(RetrieveHistorialBalanceUseCase.class);
        depositFundsUseCase = mock(DepositFundsUseCase.class);
        withdrawFundsUseCase = mock(WithdrawFundsUseCase.class);
        transferFundsUseCase = mock(TransferFundsUseCase.class);

        walletController = new WalletController(walletCreationUseCase, retrieveBalanceUseCase, retrieveHistorialBalanceUseCase, depositFundsUseCase, withdrawFundsUseCase, transferFundsUseCase);
    }

    @Test
    void whenWalletCreationIsCalledThenCreatedStatusIsReturned() {
        long userId = 1L;
        WalletCreationCommand command = new WalletCreationCommand(userId);
        CreatedWallet createdWallet = CreatedWallet.from(Wallet.of(userId));

        when(walletCreationUseCase.create(any(WalletCreationCommand.class))).thenReturn(createdWallet);

        ResponseEntity<CreatedWallet> response = walletController.createWallet(command);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdWallet, response.getBody());
        verify(walletCreationUseCase, times(1)).create(command);
    }

    @Test
    void whenBalanceRetrieveIsCallThenOKStatusShouldBeReturned() {
        long walletId = 1L;
        BalanceRetrieved balanceRetrieved = new BalanceRetrieved(walletId, "USD 100,00");

        when(retrieveBalanceUseCase.retrieve(any(RetrieveBalanceCommand.class))).thenReturn(balanceRetrieved);

        ResponseEntity<BalanceRetrieved> response = walletController.retrieveBalance(walletId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(balanceRetrieved, response.getBody());
        verify(retrieveBalanceUseCase, times(1)).retrieve(any(RetrieveBalanceCommand.class));
    }

    @Test
    void whenHistoricalBalanceRetrieveIsCallThenOKStatusShouldBeReturned() {
        long walletId = 1L;
        LocalDateTime from = LocalDateTime.now().minusDays(7);
        LocalDateTime to = LocalDateTime.now();
        HistorialBalanceRetrieved historialBalanceRetrieved = HistorialBalanceRetrieved.from(1L, 100.0f, Collections.emptyList());

        when(retrieveHistorialBalanceUseCase.retrieve(any(RetrieveHistorialBalanceCommand.class))).thenReturn(historialBalanceRetrieved);

        ResponseEntity<HistorialBalanceRetrieved> response = walletController.retrieveHistorialBalance(walletId, from, to);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(historialBalanceRetrieved, response.getBody());
        verify(retrieveHistorialBalanceUseCase, times(1)).retrieve(any(RetrieveHistorialBalanceCommand.class));
    }

    @Test
    void whenDepositFundIsSuccessfullThenCreatedStatusShouldBeReturned() {
        long walletId = 1L;
        DepositFundsCommand command = DepositFundsCommand.of(walletId, 200.f);
        long userId = 2L;
        FundsDeposited fundsDeposited = FundsDeposited.of(walletId, userId, 50.0f);

        when(depositFundsUseCase.deposit(any(DepositFundsCommand.class))).thenReturn(fundsDeposited);

        ResponseEntity<FundsDeposited> response = walletController.deposit(command);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(fundsDeposited, response.getBody());
        verify(depositFundsUseCase, times(1)).deposit(command);
    }

    @Test
    void whenFundsWithdrawedThenCreatedStatusShouldBeReturned() {
        long walletId = 1L;
        WithdrawFundsCommand command = WithdrawFundsCommand.of(walletId, 30.0f);
        long userId = 2L;
        FundsWithdrawed fundsWithdrawed = FundsWithdrawed.of(walletId, userId, 30.0f);

        when(withdrawFundsUseCase.withdraw(any(WithdrawFundsCommand.class))).thenReturn(fundsWithdrawed);

        ResponseEntity<FundsWithdrawed> response = walletController.withdrawFunds(command);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(fundsWithdrawed, response.getBody());
        verify(withdrawFundsUseCase, times(1)).withdraw(command);
    }

    @Test
    void whenTransferFundsThenThenCreatedStatusShouldBeReturned() {
        long fromWalletId = 1L;
        long toWalletId = 2L;
        TransferFundsCommand command = TransferFundsCommand.of(fromWalletId, toWalletId, BigDecimal.valueOf(20.0f));

        float transferredAmount = 10f;
        float fromWalletNewBalance = 20f;
        float toWalletNewBalance = 20f;
        FundsTransfered fundsTransfered = FundsTransfered.from(fromWalletId, toWalletId, transferredAmount, fromWalletNewBalance, toWalletNewBalance);

        when(transferFundsUseCase.transfer(any(TransferFundsCommand.class))).thenReturn(fundsTransfered);

        ResponseEntity<FundsTransfered> response = walletController.transferFunds(command);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(fundsTransfered, response.getBody());
        verify(transferFundsUseCase, times(1)).transfer(command);
    }
}