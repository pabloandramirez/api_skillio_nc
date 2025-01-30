package com.skillio.api_v1.mapper.comentario.impl;

import com.skillio.api_v1.domain.Comentario;
import com.skillio.api_v1.domain.Publicacion;
import com.skillio.api_v1.mapper.comentario.ComentarioMapper;
import com.skillio.api_v1.models.comentario.ComentarioDTO;
import com.skillio.api_v1.repository.comentario.ComentarioRepository;
import com.skillio.api_v1.repository.estudiante.EstudianteRepository;
import com.skillio.api_v1.repository.publicacion.PublicacionRepository;
import com.skillio.api_v1.repository.usuario.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ComentarioMapperImpl implements ComentarioMapper {

    private PublicacionRepository publicacionRepository;
    private final EstudianteRepository estudianteRepository;

    @Override
    public Comentario comentarioDTOtoComentario(ComentarioDTO comentarioDTO) {
        Comentario.ComentarioBuilder builder = Comentario.builder()
                .id(UUID.randomUUID())
                .comentarioTexto(comentarioDTO.getComentarioTexto())
                .fechaComentario(getLocalDate(comentarioDTO.getFechaPublicacion()));

        if (publicacionRepository.findById(UUID.fromString(comentarioDTO.getPublicacionId())).isPresent()){
            builder.publicacion(publicacionRepository.findById(UUID.fromString(comentarioDTO.getPublicacionId())).get());
        }

        if (estudianteRepository.findById(UUID.fromString(comentarioDTO.getUsuarioId())).isPresent()){
            builder.estudiante(estudianteRepository.findById(UUID.fromString(comentarioDTO.getUsuarioId())).get());
        }
        return builder.build();
    }

    @Override
    public ComentarioDTO comentarioToComentarioDTO(Comentario comentario) {
        ComentarioDTO.ComentarioDTOBuilder builder = ComentarioDTO.builder()
                .id(comentario.getId().toString())
                .usuarioId(comentario.getEstudiante().getId().toString())
                .publicacionId(comentario.getPublicacion().getId().toString())
                .comentarioTexto(comentario.getComentarioTexto())
                .fechaPublicacion(getLocalDate(comentario.getFechaComentario()));

        return builder.build();
    }

    private String getLocalDate(LocalDate localDate){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return localDate.format(formato);
    }

    private LocalDate getLocalDate(String localDate){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(localDate, formato);
    }
}
