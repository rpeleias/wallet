package com.recargapay.wallet.port.outh;

import com.recargapay.wallet.utils.MonetaryConverter;

import java.util.Locale;

public class FundsWithdrawed {

    private final Long walletId;
    private final Long userId;
    private final String amount;

    public FundsWithdrawed(Long walletId, Long userId, String amount) {
        this.walletId = walletId;
        this.userId = userId;
        this.amount = amount;
    }

    public static FundsWithdrawed of(Long id, Long userId, float amount) {
        String convertedAmount = MonetaryConverter.convert(amount, Locale.getDefault());
        return new FundsWithdrawed(id, userId, convertedAmount);
    }

    public Long getWalletId() {
        return walletId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getAmount() {
        return amount;
    }
}
