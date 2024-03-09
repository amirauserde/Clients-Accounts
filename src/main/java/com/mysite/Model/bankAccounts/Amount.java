package com.mysite.Model.bankAccounts;

import lombok.*;

import java.math.BigDecimal;
import java.util.Currency;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Amount {
    private Currency currency;
    private BigDecimal value;

}
