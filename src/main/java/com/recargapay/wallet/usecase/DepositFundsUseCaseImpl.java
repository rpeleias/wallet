package com.recargapay.wallet.usecase;

import com.recargapay.wallet.domain.Transaction;
import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.exception.WalletNotFoundException;
import com.recargapay.wallet.port.in.command.DepositFundsCommand;
import com.recargapay.wallet.port.in.usecase.DepositFundsUseCase;
import com.recargapay.wallet.port.out.FundsDeposited;
import com.recargapay.wallet.port.out.repository.WalletRepositoryPort;

public class DepositFundsUseCaseImpl implements DepositFundsUseCase {

    private final WalletRepositoryPort walletRepositoryPort;

    public DepositFundsUseCaseImpl(WalletRepositoryPort walletRepositoryPort) {
        this.walletRepositoryPort = walletRepositoryPort;
    }

    @Override
    public FundsDeposited deposit(DepositFundsCommand command) {
        Wallet wallet = walletRepositoryPort.findById(command.getWalletId()).orElseThrow(() -> new WalletNotFoundException(command.getWalletId()));

        Transaction deposit = Transaction.fromDeposit(command.getWalletId(), command.getValue());
        wallet.add(deposit);

        walletRepositoryPort.saveOrUpdate(wallet);

        return FundsDeposited.of(wallet.getId(), wallet.getUserId(), wallet.getAmount());
    }
}
