package com.mysite.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

public class ExchangeRateImpl implements ExchangeRate {
    private static final ExchangeRateImpl INSTANCE;

    static {
        INSTANCE = new ExchangeRateImpl();
    }
    private final String apiKey = "";
    private Map<Currency, BigDecimal> currencyRates;

    public static ExchangeRateImpl getInstance() {
        return INSTANCE;
    }
    private ExchangeRateImpl() {
        currencyRates = new HashMap<>();
        currencyRates.put(Currency.getInstance("USD"), BigDecimal.ONE);
        currencyRates.put(Currency.getInstance("EUR"), new BigDecimal("0.92"));
    }
    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal amount)
            throws UnsupportedOperationException {
        BigDecimal convertedAmount;
        try {
            BigDecimal sourceRate = currencyRates.get(sourceCurrency);
            BigDecimal targetRate = currencyRates.get(targetCurrency);
            convertedAmount = amount.multiply(targetRate.divide(sourceRate, 5, RoundingMode.HALF_UP));
        } catch (Exception ex) {
            throw new UnsupportedOperationException("Unfortunately just EUR and USD are supported");
        }
        return convertedAmount.setScale(2, RoundingMode.HALF_UP);
    }
}
