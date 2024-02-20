package com.mysite.Model.bankAccounts;

import com.mysite.Model.ClientType;

public enum AccountType {
    EURO(1),
    DOLLAR(2);

    private final int value;

    AccountType(int value){
        this.value = value;
    }

    public static AccountType fromValue(int value) {
        for (AccountType type:values()) {
            if (type.value == value) {
                return type;
            }
        }
        return null;
    }
}
