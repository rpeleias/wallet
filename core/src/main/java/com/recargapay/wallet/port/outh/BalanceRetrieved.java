package com.recargapay.wallet.port.outh;

import com.recargapay.wallet.utils.MonetaryConverter;

import java.util.Locale;

public class BalanceRetrieved {

    private final Long id;
    private final String amount;

    public BalanceRetrieved(Long id, String amount) {
        this.id = id;
        this.amount = amount;
    }

    public static BalanceRetrieved from(Long walletId, float amount) {
        String convertedAmount = MonetaryConverter.convert(amount, Locale.getDefault());
        return new BalanceRetrieved(walletId, convertedAmount);
    }

    public Long getId() {
        return id;
    }

    public String getAmount() {
        return amount;
    }
}
