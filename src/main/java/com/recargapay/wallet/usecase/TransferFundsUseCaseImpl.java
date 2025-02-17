package com.recargapay.wallet.usecase;

import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.exception.WalletNotFoundException;
import com.recargapay.wallet.port.in.command.TransferFundsCommand;
import com.recargapay.wallet.port.in.usecase.TransferFundsUseCase;
import com.recargapay.wallet.port.out.FundsTransfered;
import com.recargapay.wallet.port.out.repository.WalletRepositoryPort;

public class TransferFundsUseCaseImpl implements TransferFundsUseCase {

    private final WalletRepositoryPort walletRepositoryPort;

    public TransferFundsUseCaseImpl(WalletRepositoryPort walletRepositoryPort) {
        this.walletRepositoryPort = walletRepositoryPort;
    }

    @Override
    public FundsTransfered transfer(TransferFundsCommand command) {
        Wallet fromWallet = walletRepositoryPort.findById(command.getFromWalletId()).orElseThrow(() -> new WalletNotFoundException(command.getFromWalletId()));
        Wallet toWallet = walletRepositoryPort.findById(command.getToWalletId()).orElseThrow(() -> new WalletNotFoundException(command.getToWalletId()));

        fromWallet.transfer(toWallet, command.getValue().floatValue());
        return FundsTransfered.from(command.getFromWalletId(), command.getToWalletId(), command.getValue().floatValue(), fromWallet.getAmount(), toWallet.getAmount());
    }
}
