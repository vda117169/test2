package com.example.test.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    TASK_CREATOR,
    ASSIGNEE;

    @Override
    public String getAuthority() {
        return name();
    }
}
