package com.skillio.api_v1.models.amistad;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AmistadDTO {
    private String id;
    private String estadoAmistad;
    private String usuarioId1;
    private String usuarioId2;
    private String fechaAmistad;
}
