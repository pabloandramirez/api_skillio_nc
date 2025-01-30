package com.skillio.api_v1.enums;

import lombok.Getter;

@Getter
public enum Role {
    USUARIO("USUARIO"),
    ADMIN("ADMIN");

    private final String role;

    Role(String role){ this.role = role;}
}
