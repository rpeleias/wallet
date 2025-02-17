package com.recargapay.wallet.usecase;

import com.recargapay.wallet.domain.Transaction;
import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.exception.WalletNotFoundException;
import com.recargapay.wallet.port.in.command.WithdrawFundsCommand;
import com.recargapay.wallet.port.in.usecase.WithdrawFundsUseCase;
import com.recargapay.wallet.port.out.FundsWithdrawed;
import com.recargapay.wallet.port.out.repository.WalletRepositoryPort;

public class WithdrawFundsUseCaseImpl implements WithdrawFundsUseCase {

    private final WalletRepositoryPort walletRepositoryPort;

    public WithdrawFundsUseCaseImpl(WalletRepositoryPort walletRepositoryPort) {
        this.walletRepositoryPort = walletRepositoryPort;
    }

    @Override
    public FundsWithdrawed withdraw(WithdrawFundsCommand command) {
        Wallet wallet = walletRepositoryPort.findById(command.getWalletId()).orElseThrow(() -> new WalletNotFoundException(command.getWalletId()));

        Transaction deposit = Transaction.fromWithdraw(command.getWalletId(), command.getValue());
        wallet.add(deposit);

        walletRepositoryPort.saveOrUpdate(wallet);

        return FundsWithdrawed.of(wallet.getId(), wallet.getUserId(), wallet.getAmount(), wallet.getCurrency());
    }
}
