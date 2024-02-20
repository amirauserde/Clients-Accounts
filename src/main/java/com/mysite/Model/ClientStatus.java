package com.mysite.Model;

import com.mysite.util.LookupUtil;

public enum ClientStatus {
    PROSPECT("Prospect"), NEW("New"), CURRENT("Current"), PAST("Past");

    private final String name;

    public static boolean contains(String test) {

        for (ClientStatus c : values()) {
            if (c.name().equalsIgnoreCase(test)) {
                return true;
            }
        }
        return false;
    }
    static public ClientStatus lookup(String id) {
        return LookupUtil.lookup(ClientStatus.class, id);
    }

    ClientStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
