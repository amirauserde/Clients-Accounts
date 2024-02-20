package com.mysite.Model;

public enum ClientType {
    REAL(1),
    LEGAL(2);

    private final int value;

    ClientType(int value){
        this.value = value;
    }

    public static ClientType fromValue(int value) {
        for (ClientType type:values()) {
            if (type.value == value) {
                return type;
            }
        }
        return null;
    }
}
