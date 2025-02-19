package com.recargapay.wallet.port.outh;

import com.recargapay.wallet.domain.Transaction;
import com.recargapay.wallet.utils.MonetaryConverter;

import java.util.List;
import java.util.Locale;

public class HistorialBalanceRetrieved {

    private final Long id;
    private final String amount;
    private final List<Transaction> transactions;

    public HistorialBalanceRetrieved(Long id, String amount, List<Transaction> transactions) {
        this.id = id;
        this.amount = amount;
        this.transactions = transactions;
    }

    public static HistorialBalanceRetrieved from(Long walletId, float amount, List<Transaction> transactions) {
        String convertedAmount = MonetaryConverter.convert(amount, Locale.getDefault());
        return new HistorialBalanceRetrieved(walletId, convertedAmount, transactions);
    }

    public Long getId() {
        return id;
    }

    public String getAmount() {
        return amount;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
