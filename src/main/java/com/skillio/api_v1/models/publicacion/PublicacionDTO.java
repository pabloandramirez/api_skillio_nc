package com.skillio.api_v1.models.publicacion;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublicacionDTO {
    private String id;
    private String usuarioId;
    private String contenido;
    private String fechaPublicacion;
    private String visibilidad;
    private List<String> palabrasClave;
    private String nombreCompletoAutor;
    private String fotoUrlAutor;
}
