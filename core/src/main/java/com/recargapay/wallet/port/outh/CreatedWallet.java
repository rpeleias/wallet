package com.recargapay.wallet.port.outh;

import com.recargapay.wallet.domain.Wallet;
import com.recargapay.wallet.utils.MonetaryConverter;

import java.util.Locale;

public class CreatedWallet {

    private final Long id;
    private final Long userId;
    private final String amount;

    private CreatedWallet(Long id, Long userId, String amount) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
    }

    public static CreatedWallet from(Wallet savedWallet) {
        String convertedAmount = MonetaryConverter.convert(savedWallet.getAmount(), Locale.getDefault());
        return new CreatedWallet(savedWallet.getId(), savedWallet.getUserId(), convertedAmount);
    }

    public Long getUserId() {
        return userId;
    }

    public Long getId() {
        return id;
    }

    public String getAmount() {
        return amount;
    }
}
