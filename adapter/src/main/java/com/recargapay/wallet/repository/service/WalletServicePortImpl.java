package com.recargapay.wallet.repository.service;

import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.port.out.repository.WalletRepositoryPort;
import com.recargapay.wallet.repository.entity.WalletEntity;
import com.recargapay.wallet.repository.impl.WalletRepository;
import com.recargapay.wallet.repository.mapper.WalletMapper;
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
        if (optionalUserWallet.isPresent()) {
            WalletEntity walletEntity = optionalUserWallet.get();
            Wallet wallet = WalletMapper.from(walletEntity);
            return Optional.of(wallet);
        }
        return Optional.empty();
    }

    @Override
    public Wallet saveOrUpdate(Wallet newWallet) {
        WalletEntity newWalletEntity = WalletMapper.from(newWallet);
        WalletEntity savedWalletEntity = this.walletRepository.save(newWalletEntity);
        return WalletMapper.from(savedWalletEntity);
    }

    @Override
    public Optional<Wallet> findById(Long walletId) {
        Optional<WalletEntity> walletEntity = this.walletRepository.findById(walletId);
        return walletEntity.map(WalletMapper::from);
    }
}
