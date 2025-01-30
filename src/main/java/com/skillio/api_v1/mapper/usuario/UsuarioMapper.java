package com.skillio.api_v1.mapper.usuario;

import com.skillio.api_v1.domain.Usuario;
import com.skillio.api_v1.models.usuario.UsuarioDTO;

public interface UsuarioMapper {

    Usuario usuarioDTOtoUsuario(UsuarioDTO usuarioDTO);

    UsuarioDTO usuarioToUsuarioDTO(Usuario usuario);
}
