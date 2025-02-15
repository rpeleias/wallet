package com.recargapay.wallet.usecase;

import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.exception.WalletAlreadyExistsException;
import com.recargapay.wallet.port.in.WalletCreationCommand;
import com.recargapay.wallet.port.in.WalletCreationUseCase;
import com.recargapay.wallet.port.out.CreatedWallet;
import com.recargapay.wallet.port.out.WalletRepositoryPort;

public class WalletCreationUseCaseImpl implements WalletCreationUseCase {

    private final WalletRepositoryPort walletRepositoryPort;

    public WalletCreationUseCaseImpl(WalletRepositoryPort walletRepositoryPort) {
        this.walletRepositoryPort = walletRepositoryPort;
    }

    @Override
    public CreatedWallet create(WalletCreationCommand walletCreationCommand) {
        walletRepositoryPort.findByUser(walletCreationCommand.getUserId()).ifPresent(userWallet -> {
            throw new WalletAlreadyExistsException(userWallet.userId());
        });

        Wallet savedWallet = walletRepositoryPort.save(Wallet.of(walletCreationCommand.getUserId(), walletCreationCommand.getCurrency()));

        return CreatedWallet.of(savedWallet);
    }
}
