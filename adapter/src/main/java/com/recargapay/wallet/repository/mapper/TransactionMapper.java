package com.recargapay.wallet.repository.mapper;

import com.recargapay.wallet.domain.Transaction;
import com.recargapay.wallet.repository.entity.TransactionEntity;

public class TransactionMapper {

    public static Transaction from(TransactionEntity transactionEntity) {
        return Transaction.of(transactionEntity.getId(), transactionEntity.getWalletId(), transactionEntity.getType(), transactionEntity.getAmount(), transactionEntity.getTransactionDate());
    }
}
