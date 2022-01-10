package com.example.splitwise.model;

import java.math.BigDecimal;

public final class GroupBuilder {

    private Integer id;
    private String title;
    private Integer creatorId;
    private BigDecimal moneyAmount;

    public GroupBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public GroupBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public GroupBuilder withCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public GroupBuilder withMoneyAmount(BigDecimal moneyAmount) {
        this.moneyAmount = moneyAmount;
        return this;
    }

    public Group build() {
        return new Group(id, title, creatorId, moneyAmount);
    }
}
