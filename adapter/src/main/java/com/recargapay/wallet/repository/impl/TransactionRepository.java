package com.recargapay.wallet.repository.impl;

import com.recargapay.wallet.repository.entity.TransactionEntity;
import com.recargapay.wallet.repository.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    List<TransactionEntity> findByWalletIdAndTransactionDateBetween(Long walletId, LocalDateTime from, LocalDateTime to);
}
