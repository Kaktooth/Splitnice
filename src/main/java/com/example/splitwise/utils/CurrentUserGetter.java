package com.example.splitwise.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserGetter {
    public static String getActiveUserEmail() {
        return SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();
    }
}
