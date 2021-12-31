package com.example.splitwise.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class Expense {

    @NonNull
    BigDecimal amount;

    @NonNull
    LocalDateTime creationDate;

    @NonNull
    Integer landedById;

    @NonNull
    Integer landedToId;

    @NonNull
    Integer currencyId;
}
