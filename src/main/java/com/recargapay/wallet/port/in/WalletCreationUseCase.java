package com.recargapay.wallet.port.in;

import com.recargapay.wallet.port.out.CreatedWallet;

public interface WalletCreationUseCase {

    CreatedWallet create(WalletCreationCommand walletCreationCommand);
}
