package com.recargapay.wallet.port.in.usecase;

import com.recargapay.wallet.port.in.command.WalletCreationCommand;
import com.recargapay.wallet.port.outh.CreatedWallet;

public interface WalletCreationUseCase {

    CreatedWallet create(WalletCreationCommand walletCreationCommand);
}
