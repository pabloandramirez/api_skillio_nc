package com.skillio.api_v1.domain;

import com.skillio.api_v1.enums.Role;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Admin extends Usuario{

    @Builder.Default
    private Role role = Role.ADMIN;

}
