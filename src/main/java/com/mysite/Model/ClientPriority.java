package com.mysite.Model;

import com.mysite.util.LookupUtil;

public enum ClientPriority {
    CRITICAL("Critical"), HIGH("High"), MEDIUM("Medium"), LOW("Low");

    private final String name;

    public static boolean contains(String test) {

        for (ClientPriority c : values()) {
            if (c.name().equalsIgnoreCase(test)) {
                return true;
            }
        }
        return false;
    }


    static public ClientPriority lookup(String id) {
        return LookupUtil.lookup(ClientPriority.class, id);
    }


    ClientPriority(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
