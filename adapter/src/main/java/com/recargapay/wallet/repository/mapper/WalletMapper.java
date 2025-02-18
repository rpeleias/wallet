package com.recargapay.wallet.repository.mapper;

import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.repository.entity.WalletEntity;

public class WalletMapper {

    public static Wallet from(WalletEntity wallet) {
        return Wallet.of(null);
    }
}
