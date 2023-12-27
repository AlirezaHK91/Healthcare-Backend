package com.example.healthAppStarter.models;

public enum ERole {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private String value;

    ERole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
