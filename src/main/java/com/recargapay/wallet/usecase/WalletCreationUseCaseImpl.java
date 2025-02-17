package com.recargapay.wallet.usecase;

import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.exception.WalletAlreadyExistsException;
import com.recargapay.wallet.port.in.command.WalletCreationCommand;
import com.recargapay.wallet.port.in.usecase.WalletCreationUseCase;
import com.recargapay.wallet.port.out.CreatedWallet;
import com.recargapay.wallet.port.out.repository.WalletRepositoryPort;

public class WalletCreationUseCaseImpl implements WalletCreationUseCase {

    private final WalletRepositoryPort walletRepositoryPort;

    public WalletCreationUseCaseImpl(WalletRepositoryPort walletRepositoryPort) {
        this.walletRepositoryPort = walletRepositoryPort;
    }

    @Override
    public CreatedWallet create(WalletCreationCommand walletCreationCommand) {
        walletRepositoryPort.findByUser(walletCreationCommand.getUserId()).ifPresent(userWallet -> {
            throw new WalletAlreadyExistsException(userWallet.getUserId());
        });

        Wallet savedWallet = walletRepositoryPort.saveOrUpdate(Wallet.of(walletCreationCommand.getUserId()));

        return CreatedWallet.from(savedWallet);
    }
}
