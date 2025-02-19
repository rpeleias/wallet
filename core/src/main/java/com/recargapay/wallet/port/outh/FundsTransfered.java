package com.recargapay.wallet.port.outh;

import com.recargapay.wallet.utils.MonetaryConverter;

import java.util.Locale;

public class FundsTransfered {

    private final Long fromWalletId;
    private final Long toWalletId;
    private final String transferredAmount;
    private final String fromWalletNewBalance;
    private final String toWalletNewBalance;

    private FundsTransfered(Long fromWalletId, Long toWalletId, String transferredAmount, String fromWalletNewBalance, String toWalletNewBalance) {
        this.fromWalletId = fromWalletId;
        this.toWalletId = toWalletId;
        this.transferredAmount = transferredAmount;
        this.fromWalletNewBalance = fromWalletNewBalance;
        this.toWalletNewBalance = toWalletNewBalance;
    }

    public static FundsTransfered from(Long fromWalletId, Long toWalletId, float transferredAmount, float fromWalletNewBalance, float toWalletNewBalance) {
        String convertedTransferredAmount = MonetaryConverter.convert(transferredAmount, Locale.getDefault());
        String convertedFromWalletNewBalance = MonetaryConverter.convert(fromWalletNewBalance, Locale.getDefault());
        String convertedToWalletNewBalance = MonetaryConverter.convert(toWalletNewBalance, Locale.getDefault());

        return new FundsTransfered(fromWalletId, toWalletId, convertedTransferredAmount, convertedFromWalletNewBalance, convertedToWalletNewBalance);
    }

    public Long getFromWalletId() {
        return fromWalletId;
    }

    public Long getToWalletId() {
        return toWalletId;
    }

    public String getTransferredAmount() {
        return transferredAmount;
    }

    public String getFromWalletNewBalance() {
        return fromWalletNewBalance;
    }

    public String getToWalletNewBalance() {
        return toWalletNewBalance;
    }
}
