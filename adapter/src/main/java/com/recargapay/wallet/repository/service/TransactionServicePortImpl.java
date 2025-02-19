package com.recargapay.wallet.repository.service;

import com.recargapay.wallet.domain.Transaction;
import com.recargapay.wallet.port.outh.repository.TransactionRepositoryPort;
import com.recargapay.wallet.repository.entity.TransactionEntity;
import com.recargapay.wallet.repository.impl.TransactionRepository;
import com.recargapay.wallet.repository.mapper.TransactionMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServicePortImpl implements TransactionRepositoryPort {

    private final TransactionRepository transactionRepository;

    public TransactionServicePortImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> findByWalletIdBetweenDates(Long walletId, LocalDateTime from, LocalDateTime to) {
        List<TransactionEntity> transactionsEntity = transactionRepository.findByWalletIdAndTransactionDateBetween(walletId, from, to);
        List<Transaction> transactionList = transactionsEntity.stream().map(TransactionMapper::from).toList();
        return transactionList;
    }
}
