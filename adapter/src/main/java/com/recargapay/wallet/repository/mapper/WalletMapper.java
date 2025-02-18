package com.recargapay.wallet.repository.mapper;

import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.repository.entity.WalletEntity;

public class WalletMapper {

    public static Wallet from(WalletEntity wallet) {
        return Wallet.of(wallet.getId(), wallet.getUserId(), wallet.getCreationDate(), wallet.getAmount());
    }

    public static WalletEntity from(Wallet wallet) {
        return new WalletEntity(wallet.getId(), wallet.getUserId(), wallet.getCreationDate(), wallet.getAmount());
    }
}
