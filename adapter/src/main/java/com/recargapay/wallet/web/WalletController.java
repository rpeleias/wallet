package com.recargapay.wallet.web;

import com.recargapay.wallet.port.in.command.WalletCreationCommand;
import com.recargapay.wallet.port.in.usecase.DepositFundsUseCase;
import com.recargapay.wallet.port.in.usecase.RetrieveBalanceUseCase;
import com.recargapay.wallet.port.in.usecase.RetrieveHistorialBalanceUseCase;
import com.recargapay.wallet.port.in.usecase.WalletCreationUseCase;
import com.recargapay.wallet.port.in.usecase.WithdrawFundsUseCase;
import com.recargapay.wallet.port.out.CreatedWallet;
import com.recargapay.wallet.port.out.repository.TransactionRepositoryPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    private final WalletCreationUseCase walletCreationUseCase;
    private final RetrieveBalanceUseCase retrieveBalanceUseCase;
    private final RetrieveHistorialBalanceUseCase retrieveHistorialBalanceUseCase;
    private final DepositFundsUseCase depositFundsUseCase;
    private final WithdrawFundsUseCase withdrawFundsUseCase;
    private final TransactionRepositoryPort transactionRepositoryPort;

    public WalletController(WalletCreationUseCase walletCreationUseCase, RetrieveBalanceUseCase retrieveBalanceUseCase, RetrieveHistorialBalanceUseCase retrieveHistorialBalanceUseCase, DepositFundsUseCase depositFundsUseCase, WithdrawFundsUseCase withdrawFundsUseCase, TransactionRepositoryPort transactionRepositoryPort) {
        this.walletCreationUseCase = walletCreationUseCase;
        this.retrieveBalanceUseCase = retrieveBalanceUseCase;
        this.retrieveHistorialBalanceUseCase = retrieveHistorialBalanceUseCase;
        this.depositFundsUseCase = depositFundsUseCase;
        this.withdrawFundsUseCase = withdrawFundsUseCase;
        this.transactionRepositoryPort = transactionRepositoryPort;
    }

    @PostMapping
    public ResponseEntity<CreatedWallet> createWallet(@RequestBody WalletCreationCommand command) {
        CreatedWallet createdWallet = walletCreationUseCase.create(command);
        return ResponseEntity.ok(createdWallet);
    }
}
