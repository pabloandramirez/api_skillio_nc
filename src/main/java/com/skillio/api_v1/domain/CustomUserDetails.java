package com.skillio.api_v1.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

public class CustomUserDetails extends User {
    private final UUID uuid;
    private final String nombreCompleto;
    // Puedes agregar otros campos que necesites

    public CustomUserDetails(String username, String password,
                             Collection<? extends GrantedAuthority> authorities,
                             UUID uuid, String nombreCompleto) {
        super(username, password, authorities);
        this.uuid = uuid;
        this.nombreCompleto = nombreCompleto;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getNombreCompleto(){
        return nombreCompleto;
    }
}
