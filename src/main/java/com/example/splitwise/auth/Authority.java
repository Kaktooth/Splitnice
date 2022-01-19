package com.example.splitwise.auth;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Authority {

    USER(0), ADMIN(1);

    private final Integer numVal;

    Authority(Integer numVal) {
        this.numVal = numVal;
    }

    public Integer getNumVal() {
        return numVal;
    }
}
