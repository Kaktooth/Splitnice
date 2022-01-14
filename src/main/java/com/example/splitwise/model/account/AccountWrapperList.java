package com.example.splitwise.model.account;

import com.example.splitwise.model.WrappedList;

import java.io.Serializable;
import java.util.List;

public class AccountWrapperList implements Serializable {

    private List<Account> accounts;

    public AccountWrapperList(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Account> getWrappedItems() {
        return null;
    }
}
