package com.catchvbackend.domain;

import lombok.Getter;

@Getter
public enum AccountRole {
    ADMIN("Administrator"),
    USER("Regular User");

    private final String description;
    AccountRole(String description) {
        this.description = description;
    }
}
