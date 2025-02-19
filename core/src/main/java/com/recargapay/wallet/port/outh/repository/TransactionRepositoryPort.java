package com.recargapay.wallet.port.outh.repository;

import com.recargapay.wallet.domain.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepositoryPort {

    List<Transaction> findByWalletIdBetweenDates(Long walletId, LocalDateTime from, LocalDateTime to);
}
