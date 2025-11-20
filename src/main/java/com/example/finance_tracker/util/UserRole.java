package com.example.finance_tracker.util;

import java.util.Arrays;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserRole {

    public final String ROLE_USER = "ROLE_USER";
    public final String ROLE_ADMIN = "ROLE_ADMIN";

    public List<String> getAll() {
        return Arrays.asList(ROLE_USER, ROLE_ADMIN);
    }
}
