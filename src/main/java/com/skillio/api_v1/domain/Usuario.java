package com.skillio.api_v1.domain;

import com.skillio.api_v1.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
public class Usuario {

    @Id
    @GeneratedValue(generator = "UUID")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 50, columnDefinition = "varchar(50)", updatable = false, nullable = false)
    private UUID id;

    @Column(length = 150, columnDefinition = "varchar(150)", updatable = true, nullable = false)
    private String nombreCompleto;

    @Column(length = 150, columnDefinition = "varchar(150)", updatable = true, nullable = false)
    private String email;

    @Column(nullable = false, updatable = true)
    private String password;

    @Transient
    private Role role;

}
