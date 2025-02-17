package com.recargapay.wallet.repository.service;

import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.port.out.repository.WalletRepositoryPort;
import com.recargapay.wallet.repository.impl.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletServicePortImpl implements WalletRepositoryPort {

    private final WalletRepository walletRepository;

    public WalletServicePortImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public Optional<Wallet> findByUser(Long userId) {
        return Optional.empty();
    }

    @Override
    public Wallet saveOrUpdate(Wallet newWallet) {
        return null;
    }

    @Override
    public Optional<Wallet> findById(Long walletId) {
        return Optional.empty();
    }
}
