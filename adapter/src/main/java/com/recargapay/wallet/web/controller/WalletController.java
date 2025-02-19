package com.recargapay.wallet.web.controller;

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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/wallets")
public class WalletController implements WalletControllerDocs {

    private final WalletCreationUseCase walletCreationUseCase;
    private final RetrieveBalanceUseCase retrieveBalanceUseCase;
    private final RetrieveHistorialBalanceUseCase retrieveHistorialBalanceUseCase;
    private final DepositFundsUseCase depositFundsUseCase;
    private final WithdrawFundsUseCase withdrawFundsUseCase;
    private final TransferFundsUseCase transferFundsUseCase;

    public WalletController(WalletCreationUseCase walletCreationUseCase, RetrieveBalanceUseCase retrieveBalanceUseCase, RetrieveHistorialBalanceUseCase retrieveHistorialBalanceUseCase, DepositFundsUseCase depositFundsUseCase, WithdrawFundsUseCase withdrawFundsUseCase, TransferFundsUseCase transferFundsUseCase) {
        this.walletCreationUseCase = walletCreationUseCase;
        this.retrieveBalanceUseCase = retrieveBalanceUseCase;
        this.retrieveHistorialBalanceUseCase = retrieveHistorialBalanceUseCase;
        this.depositFundsUseCase = depositFundsUseCase;
        this.withdrawFundsUseCase = withdrawFundsUseCase;
        this.transferFundsUseCase = transferFundsUseCase;
    }

    @PostMapping
    public ResponseEntity<CreatedWallet> createWallet(@RequestBody WalletCreationCommand command) {
        CreatedWallet createdWallet = walletCreationUseCase.create(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWallet);
    }

    @GetMapping("{walletId}/balance")
    public ResponseEntity<BalanceRetrieved> retrieveBalance(@PathVariable Long walletId) {
        RetrieveBalanceCommand command = RetrieveBalanceCommand.of(walletId);
        BalanceRetrieved balanceRetrieved = retrieveBalanceUseCase.retrieve(command);
        return ResponseEntity.ok(balanceRetrieved);
    }

    @GetMapping("{walletId}/balance/history")
    public ResponseEntity<HistorialBalanceRetrieved> retrieveHistorialBalance(@PathVariable Long walletId, @RequestParam @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss") LocalDateTime from, @RequestParam @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss") LocalDateTime to) {
        RetrieveHistorialBalanceCommand command = RetrieveHistorialBalanceCommand.of(walletId, from, to);
        HistorialBalanceRetrieved historialBalanceRetrieved = retrieveHistorialBalanceUseCase.retrieve(command);
        return ResponseEntity.ok(historialBalanceRetrieved);
    }

    @PostMapping("/deposit")
    public ResponseEntity<FundsDeposited> deposit(@RequestBody DepositFundsCommand command) {
        FundsDeposited fundsDeposited = depositFundsUseCase.deposit(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(fundsDeposited);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<FundsWithdrawed> withdrawFunds(@RequestBody WithdrawFundsCommand command) {
        FundsWithdrawed fundsWithdrawed = withdrawFundsUseCase.withdraw(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(fundsWithdrawed);
    }

    @PostMapping("/transfer")
    public ResponseEntity<FundsTransfered> transferFunds(@RequestBody TransferFundsCommand command) {
        FundsTransfered fundsTransfered = transferFundsUseCase.transfer(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(fundsTransfered);
    }
}
