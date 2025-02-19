package com.recargapay.wallet.port.outh;

import com.recargapay.wallet.utils.MonetaryConverter;

import java.util.Locale;

public class FundsDeposited {

    private final Long walletId;
    private final Long userId;
    private final String amount;

    private FundsDeposited(Long walletId, Long userId, String amount) {
        this.walletId = walletId;
        this.userId = userId;
        this.amount = amount;
    }

    public static FundsDeposited of(Long id, Long userId, float amount) {
        String convertedAmount = MonetaryConverter.convert(amount, Locale.getDefault());
        return new FundsDeposited(id, userId, convertedAmount);
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
