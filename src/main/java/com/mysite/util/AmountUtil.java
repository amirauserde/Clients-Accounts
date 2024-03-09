package com.mysite.util;

import com.mysite.Model.bankAccounts.Amount;

import java.math.BigDecimal;

public class AmountUtil {

    private static final AmountUtil INSTANCE;
    private ExchangeRateImpl exchangeRate;

    public static AmountUtil getInstance() {
        return INSTANCE;
    }

    static {
        INSTANCE = new AmountUtil();
    }

    private AmountUtil() {
        this.exchangeRate = ExchangeRateImpl.getInstance();
    }

    public Amount add(Amount firstAmount, Amount secondAmount) {
        BigDecimal convertedAmount = exchangeRate.convert(secondAmount.getCurrency(),
                firstAmount.getCurrency(), secondAmount.getValue());
        return new Amount(firstAmount.getCurrency(), firstAmount.getValue().add(convertedAmount));
    }

    public Amount subtract(Amount firstAmount, Amount secondAmount) {
        BigDecimal convertedAmount = exchangeRate.convert(secondAmount.getCurrency(),
                firstAmount.getCurrency(), secondAmount.getValue());
        return new Amount(firstAmount.getCurrency(), firstAmount.getValue().subtract(convertedAmount));
    }

    public int compareTo(Amount firstAmount, Amount secondAmount) {
        BigDecimal convertedAmount = exchangeRate.convert(secondAmount.getCurrency(),
                firstAmount.getCurrency(), secondAmount.getValue());
        return firstAmount.getValue().compareTo(convertedAmount);
    }
}
