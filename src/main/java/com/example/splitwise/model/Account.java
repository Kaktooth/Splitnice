package com.example.splitwise.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Value
@Builder
public class Account extends BusinessEntity {

    @NonNull
    Integer id;

    @NonNull
    String username;

    @NonNull
    String email;

    @NonNull
    String phone;

    @NonNull
    BigDecimal moneyAmount;
}
