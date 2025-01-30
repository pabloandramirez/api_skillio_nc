package com.skillio.api_v1.mapper.usuario.impl;

import com.skillio.api_v1.domain.Usuario;
import com.skillio.api_v1.mapper.usuario.UsuarioMapper;
import com.skillio.api_v1.models.usuario.UsuarioDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class UsuarioMapperImpl implements UsuarioMapper {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Usuario usuarioDTOtoUsuario(UsuarioDTO usuarioDTO) {
        Usuario.UsuarioBuilder builder = Usuario.builder()
                .id(UUID.randomUUID())
                .nombreCompleto(usuarioDTO.getNombreCompleto())
                .email(usuarioDTO.getEmail())
                .password(passwordEncoder.encode(usuarioDTO.getPassword()));
        return builder.build();
    }

    @Override
    public UsuarioDTO usuarioToUsuarioDTO(Usuario usuario) {
        UsuarioDTO.UsuarioDTOBuilder builder = UsuarioDTO.builder()
                .id(String.valueOf(usuario.getId()))
                .nombreCompleto(usuario.getNombreCompleto())
                .email(usuario.getEmail())
                .role(String.valueOf(usuario.getRole()));
        return builder.build();
    }
}
