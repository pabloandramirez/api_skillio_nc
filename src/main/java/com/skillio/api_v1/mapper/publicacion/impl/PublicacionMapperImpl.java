package com.skillio.api_v1.mapper.publicacion.impl;

import com.skillio.api_v1.domain.Comentario;
import com.skillio.api_v1.domain.Publicacion;
import com.skillio.api_v1.enums.Visibilidad;
import com.skillio.api_v1.mapper.publicacion.PublicacionMapper;
import com.skillio.api_v1.models.publicacion.PublicacionDTO;
import com.skillio.api_v1.repository.estudiante.EstudianteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PublicacionMapperImpl implements PublicacionMapper {

    private final EstudianteRepository estudianteRepository;

    @Override
    public Publicacion publicacionDTOtoPublicacion(PublicacionDTO publicacionDTO) {
        Publicacion.PublicacionBuilder builder = Publicacion.builder()
                .content(publicacionDTO.getContenido())
                .fechaPublicacion(LocalDate.now())
                .visibilidad(Visibilidad.valueOf(publicacionDTO.getVisibilidad().toUpperCase()))
                .palabrasClave(publicacionDTO.getPalabrasClave());

        if(estudianteRepository.findById(UUID.fromString(publicacionDTO.getUsuarioId())).isPresent()){
            builder.estudiante(estudianteRepository.findById(UUID.fromString(publicacionDTO.getUsuarioId())).get());
        }


        return builder.build();
    }

    @Override
    public PublicacionDTO publicacionToPublicacionDTO(Publicacion publicacion) {
        PublicacionDTO.PublicacionDTOBuilder builder = PublicacionDTO.builder()
                .id(publicacion.getId().toString())
                .usuarioId(publicacion.getEstudiante().getId().toString())
                .contenido(publicacion.getContent())
                .fechaPublicacion(getLocalDate(publicacion.getFechaPublicacion()))
                .visibilidad(publicacion.getVisibilidad().toString())
                .palabrasClave(publicacion.getPalabrasClave())
                .nombreCompletoAutor(publicacion.getEstudiante().getNombreCompleto());

        if (publicacion.getEstudiante().getImagenPerfilUrl()!=null && !publicacion.getEstudiante().getImagenPerfilUrl().isBlank()){
            builder.fotoUrlAutor(publicacion.getEstudiante().getImagenPerfilUrl());
        }

        if(publicacion.getComentarios()!= null && !publicacion.getComentarios().isEmpty()){
            builder.listaComentarios(publicacion.getComentarios().stream().map(Comentario::toString).collect(Collectors.toList()));
        }

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
