package com.example.splitwise.model.group;

import com.example.splitwise.model.Identifiable;

import java.math.BigDecimal;

public class Group implements Identifiable {

    private final Integer id;
    private final String title;
    private final Integer creatorId;

    public Group(Integer id, String title, Integer creatorId) {
        this.id = id;
        this.title = title;
        this.creatorId = creatorId;
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
}
