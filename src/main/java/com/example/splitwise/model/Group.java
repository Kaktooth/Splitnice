package com.example.splitwise.model;

import java.math.BigDecimal;

public class Group extends BusinessEntity {

    private final String title;
    private final Integer creatorId;
    private final BigDecimal moneyAmount;

    public Group(Integer id, String title, Integer creatorId, BigDecimal moneyAmount) {
        super(id);
        this.title = title;
        this.creatorId = creatorId;
        this.moneyAmount = moneyAmount;
    }

    public String getTitle() {
        return title;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public BigDecimal getMoneyAmount() {
        return moneyAmount;
    }
}
