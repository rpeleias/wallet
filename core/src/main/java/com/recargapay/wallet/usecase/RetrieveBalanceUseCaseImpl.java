package com.recargapay.wallet.usecase;

import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.exception.WalletNotFoundException;
import com.recargapay.wallet.port.in.command.RetrieveBalanceCommand;
import com.recargapay.wallet.port.in.usecase.RetrieveBalanceUseCase;
import com.recargapay.wallet.port.outh.BalanceRetrieved;
import com.recargapay.wallet.port.outh.repository.WalletRepositoryPort;

public class RetrieveBalanceUseCaseImpl implements RetrieveBalanceUseCase {

    private final WalletRepositoryPort walletRepositoryPort;

    public RetrieveBalanceUseCaseImpl(WalletRepositoryPort walletRepositoryPort) {
        this.walletRepositoryPort = walletRepositoryPort;
    }

    @Override
    public BalanceRetrieved retrieve(RetrieveBalanceCommand command) {
        Wallet wallet = walletRepositoryPort.findById(command.getWalletId()).orElseThrow(() -> new WalletNotFoundException(command.getWalletId()));
        return BalanceRetrieved.from(wallet.getId(), wallet.getAmount());
    }
}
