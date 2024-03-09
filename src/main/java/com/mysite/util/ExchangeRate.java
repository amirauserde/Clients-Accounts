package com.mysite.util;

import java.math.BigDecimal;
import java.util.Currency;

public interface ExchangeRate {
     BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal amount)
            throws UnsupportedOperationException;


}
