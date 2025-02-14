package com.recargapay.wallet.port.in;

import com.recargapay.wallet.port.out.WalletCreation;

public interface WalletCreationUseCase {

    WalletCreation create(WalletCreationCommand walletCreationCommand);
}
