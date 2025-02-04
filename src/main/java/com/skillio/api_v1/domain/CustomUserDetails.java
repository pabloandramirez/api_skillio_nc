package com.skillio.api_v1.domain;

import com.skillio.api_v1.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CustomUserDetails extends User {
    private final UUID uuid;
    private final String nombreCompleto;
    // Puedes agregar otros campos que necesites

    public CustomUserDetails(String username, String password,
                             Role role,
                             UUID uuid, String nombreCompleto) {
        super(username, password, convertirRoleAGrantedAuthorities(role));
        this.uuid = uuid;
        this.nombreCompleto = nombreCompleto;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getNombreCompleto(){
        return nombreCompleto;
    }

    private static List<GrantedAuthority> convertirRoleAGrantedAuthorities(Role role) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
}
