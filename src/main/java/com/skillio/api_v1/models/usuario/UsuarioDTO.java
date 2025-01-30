package com.skillio.api_v1.models.usuario;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {
    private String id;
    private String nombreCompleto;
    private String email;
    private String password;
    private String role;
}
