package com.skillio.api_v1.models.comentario;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComentarioDTO {
    private String id;
    private String usuarioId;
    private String publicacionId;
    private String comentarioTexto;
    private String fechaPublicacion;
}
