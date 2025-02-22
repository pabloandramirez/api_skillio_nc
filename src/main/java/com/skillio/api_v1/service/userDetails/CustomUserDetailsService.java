package com.skillio.api_v1.service.userDetails;

import com.skillio.api_v1.domain.CustomUserDetails;
import com.skillio.api_v1.domain.Usuario;
import com.skillio.api_v1.repository.usuario.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new CustomUserDetails(
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.getRole(),
                usuario.getId(),
                usuario.getNombreCompleto()
        );
    }
}
