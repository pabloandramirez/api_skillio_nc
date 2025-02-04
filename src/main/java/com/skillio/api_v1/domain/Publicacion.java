package com.skillio.api_v1.domain;

import com.skillio.api_v1.enums.Visibilidad;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Publicacion {

    @Id
    @GeneratedValue(generator = "UUID")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 50, columnDefinition = "varchar(50)", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "estudiante_id", referencedColumnName = "id")
    private Estudiante estudiante;

    @Column(length = 500, columnDefinition = "varchar(500)", updatable = true, nullable = false)
    private String content;

    private LocalDate fechaPublicacion;

    @Enumerated(EnumType.STRING)
    @Column(length = 15, columnDefinition = "varchar(15)", updatable = true, nullable = false)
    private Visibilidad visibilidad;

    @OneToMany(mappedBy = "publicacion")
    private List<Comentario> comentarios = new ArrayList<>();

    @ElementCollection
    private List<String> palabrasClave = new ArrayList<>();

}
