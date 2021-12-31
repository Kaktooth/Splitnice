package com.example.splitwise.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
@Builder(toBuilder = true)

public class User extends BusinessEntity {

    @NonNull
    Integer id;

    @NonNull
    String username;

    @NonNull
    String email;

    @NonNull
    String phone;
}
