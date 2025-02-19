package com.recargapay.wallet.port.outh.repository;

import com.recargapay.wallet.domain.Wallet;

import java.util.Optional;

public interface WalletRepositoryPort {

    Optional<Wallet> findByUser(Long userId);

    Wallet saveOrUpdate(Wallet newWallet);

    Optional<Wallet> findById(Long walletId);
}
