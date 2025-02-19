package com.recargapay.wallet.repository.mapper;

import com.recargapay.wallet.domain.Transaction;
import com.recargapay.wallet.repository.entity.TransactionEntity;
import com.recargapay.wallet.repository.entity.WalletEntity;

public class TransactionMapper {

    public static Transaction from(TransactionEntity transactionEntity) {
        return Transaction.of(transactionEntity.getId(), transactionEntity.getWallet().getId(), transactionEntity.getType(), transactionEntity.getAmount(), transactionEntity.getTransactionDate());
    }

    public static TransactionEntity from(Transaction transaction) {
        return new TransactionEntity(new WalletEntity(transaction.getWalletId()), transaction.getType(), transaction.getAmount(), transaction.getTransactionDate());
    }
}
