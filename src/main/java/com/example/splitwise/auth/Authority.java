package com.example.splitwise.auth;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Authority {

    USER(0,"USER"), ADMIN(1,"ADMIN");

    private final Integer numVal;

    private final String string;

    private static final Map<Integer, Authority> lookup = new HashMap<>();

    static {
        for (Authority w : EnumSet.allOf(Authority.class))
            lookup.put(w.getNumVal(), w);
    }

    Authority(Integer numVal, String string) {
        this.numVal = numVal;
        this.string = string;
    }

    public Integer getNumVal() {
        return numVal;
    }

    public String getName() {
        return string;
    }

    public static Authority get(int code) {
        return lookup.get(code);
    }

    public static String getString(int code) {
        return lookup.get(code).getName();
    }

    public static int getEnumLength() {
        return lookup.entrySet().size();
    }
}
