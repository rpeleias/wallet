package com.recargapay.wallet.repository.service;

import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.port.out.repository.WalletRepositoryPort;
import com.recargapay.wallet.repository.entity.WalletEntity;
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
        Optional<WalletEntity> optionalUserWallet = walletRepository.findByUserId(userId);
        WalletEntity walletEntity = optionalUserWallet.get();
        Wallet wallet = Wallet.of(walletEntity.getUserId(), walletEntity.getCreationDate(), walletEntity.getAmount());
        return Optional.of(wallet);
    }

    @Override
    public Wallet saveOrUpdate(Wallet newWallet) {
        this.walletRepository.save(null);
        return null;
    }

    @Override
    public Optional<Wallet> findById(Long walletId) {
        Optional<WalletEntity> walletEntity = this.walletRepository.findById(walletId);
        return Optional.empty();
    }
}
