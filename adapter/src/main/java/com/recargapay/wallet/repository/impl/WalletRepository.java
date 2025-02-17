package com.recargapay.wallet.repository.impl;

import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.repository.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<WalletEntity, Long> {
}
