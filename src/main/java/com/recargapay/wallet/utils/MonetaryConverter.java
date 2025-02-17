package com.recargapay.wallet.utils;

import org.javamoney.moneta.Money;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

public class MonetaryConverter {

    public static String convert(float value, Locale locale) {
        MonetaryAmount monetaryAmount = Money.of(value, Monetary.getCurrency("USD"));

        MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(new Locale(locale.getLanguage(), locale.getCountry()));
        return format.format(monetaryAmount);
    }
}
