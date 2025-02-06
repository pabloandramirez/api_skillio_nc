package com.skillio.api_v1.domain;

import com.skillio.api_v1.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
public class Estudiante extends  Usuario{

    @Column(length = 15, columnDefinition = "varchar(15)", nullable = true)
    private String telefono;

    private LocalDate fechaNacimiento;

    private LocalDate fechaRegistro;

    @Column(length = 500, columnDefinition = "varchar(500)", updatable = true, nullable = true)
    private String biografia;

    @Column(length = 500, columnDefinition = "varchar(500)", updatable = true, nullable = true)
    private String imagenPerfilUrl;

    @OneToMany(mappedBy = "usuario1", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Amistad> amistadesEnviadas = new ArrayList<>();

    @OneToMany(mappedBy = "usuario2", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Amistad> amistadesRecibidas = new ArrayList<>();

    @OneToMany(mappedBy = "estudiante")
    private List<Publicacion> publicaciones = new ArrayList<>();

    @OneToMany(mappedBy = "estudiante")
    private List<Comentario> comentarios = new ArrayList<>();

    @Column(length = 150, columnDefinition = "varchar(150)", updatable = true, nullable = true)
    private String institucion;

    @Column(length = 150, columnDefinition = "varchar(150)", updatable = true, nullable = true)
    private String educacion;

    private List<String> preferencias;

    @Builder.Default
    private Role role = Role.USUARIO;

    @Override
    public String toString() {
        return "Estudiante{" +
                ", nombreCompleto='" + getNombreCompleto() + '\'' +
                ", imagenPerfilUrl='" + imagenPerfilUrl + '\'' +
                ", educacion='" + educacion + '\'' +
                '}';
    }
}
