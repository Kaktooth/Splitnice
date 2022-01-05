package com.example.splitwise.model;

import java.math.BigDecimal;

public class Group implements Identifiable {

    private final Integer id;
    private final String title;
    private final Integer creatorId;
    private final BigDecimal moneyAmount;

    public Group(Integer id, String title, Integer creatorId, BigDecimal moneyAmount) {
        this.id = id;
        this.title = title;
        this.creatorId = creatorId;
        this.moneyAmount = moneyAmount;
    }

    @Override
    public Integer getId() {
        return id;
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
