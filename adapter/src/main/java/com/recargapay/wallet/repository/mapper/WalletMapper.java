package com.recargapay.wallet.repository.mapper;

import com.recargapay.wallet.domain.Transaction;
import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.repository.entity.TransactionEntity;
import com.recargapay.wallet.repository.entity.WalletEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WalletMapper {

    public static Wallet from(WalletEntity wallet) {
        List<TransactionEntity> transactionEntities = wallet.getTransactions();
        List<Transaction> transactions = transactionEntities.stream()
                .map(TransactionMapper::from)
                .collect(Collectors.toCollection(ArrayList::new));
        return Wallet.of(wallet.getId(), wallet.getUserId(), wallet.getCreationDate(), wallet.getAmount(), transactions);
    }

    public static WalletEntity from(Wallet wallet) {
        List<TransactionEntity> transactions = wallet.getTransactions().stream().map(TransactionMapper::from).toList();
        return new WalletEntity(wallet.getId(), wallet.getUserId(), wallet.getCreationDate(), wallet.getAmount(), transactions);
    }
}
