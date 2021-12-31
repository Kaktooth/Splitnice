package com.example.splitwise.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)

public class User implements Identifiable {

    @NonNull
    Integer id;

    @NonNull
    String username;

    @NonNull
    String email;

    @NonNull
    String phone;
}
