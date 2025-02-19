package com.recargapay.wallet.usecase;

import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.exception.WalletAlreadyExistsException;
import com.recargapay.wallet.port.in.command.WalletCreationCommand;
import com.recargapay.wallet.port.in.usecase.WalletCreationUseCase;
import com.recargapay.wallet.port.outh.CreatedWallet;
import com.recargapay.wallet.port.outh.repository.WalletRepositoryPort;

public class WalletCreationUseCaseImpl implements WalletCreationUseCase {

    private final WalletRepositoryPort walletRepositoryService;

    public WalletCreationUseCaseImpl(WalletRepositoryPort walletRepositoryService) {
        this.walletRepositoryService = walletRepositoryService;
    }

    @Override
    public CreatedWallet create(WalletCreationCommand walletCreationCommand) {
        walletRepositoryService.findByUser(walletCreationCommand.getUserId()).ifPresent(userWallet -> {
            throw new WalletAlreadyExistsException(userWallet.getUserId());
        });

        Wallet savedWallet = walletRepositoryService.saveOrUpdate(Wallet.of(walletCreationCommand.getUserId()));

        return CreatedWallet.from(savedWallet);
    }
}
