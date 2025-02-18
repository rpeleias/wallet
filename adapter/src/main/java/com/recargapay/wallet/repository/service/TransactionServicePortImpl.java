package com.recargapay.wallet.repository.service;

import com.recargapay.wallet.domain.Transaction;
import com.recargapay.wallet.port.out.repository.TransactionRepositoryPort;
import com.recargapay.wallet.repository.entity.TransactionEntity;
import com.recargapay.wallet.repository.impl.TransactionRepository;
import com.recargapay.wallet.repository.impl.WalletRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServicePortImpl implements TransactionRepositoryPort {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;


    public TransactionServicePortImpl(TransactionRepository transactionRepository, WalletRepository walletRepository) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    public List<Transaction> findByWalletIdBetweenDates(Long walletId, LocalDateTime from, LocalDateTime to) {
        List<TransactionEntity> byWalletIdAndTransactionDateBetween = transactionRepository.findByWalletIdAndTransactionDateBetween(walletId, from, to);
        return List.of();
    }
}
