package com.example.splitwise.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Value
@AllArgsConstructor

public class Group extends BusinessEntity {

    @NonNull
    Integer id;

    @NonNull
    String title;

    @NonNull
    Integer creatorId;

    @NonNull
    BigDecimal moneyAmount;
}
