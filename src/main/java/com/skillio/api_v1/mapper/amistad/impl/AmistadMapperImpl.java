package com.skillio.api_v1.mapper.amistad.impl;

import com.skillio.api_v1.domain.Amistad;
import com.skillio.api_v1.enums.EstadoAmistad;
import com.skillio.api_v1.mapper.amistad.AmistadMapper;
import com.skillio.api_v1.models.amistad.AmistadDTO;
import com.skillio.api_v1.repository.estudiante.EstudianteRepository;
import com.skillio.api_v1.repository.usuario.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
@AllArgsConstructor
public class AmistadMapperImpl implements AmistadMapper {

    private final EstudianteRepository estudianteRepository;

    @Override
    public Amistad amistadDTOtoAmistad(AmistadDTO amistadDTO) {
        Amistad.AmistadBuilder builder = Amistad.builder()
                .fechaAmistad(LocalDate.now())
                .estadoAmistad(EstadoAmistad.valueOf(amistadDTO.getEstadoAmistad()));

        if (estudianteRepository.findById(UUID.fromString(amistadDTO.getUsuarioId1())).isPresent()){
            builder.usuario1(estudianteRepository.findById(UUID.fromString(amistadDTO.getUsuarioId1())).get());
        }

        if (estudianteRepository.findById(UUID.fromString(amistadDTO.getUsuarioId2())).isPresent()){
            builder.usuario1(estudianteRepository.findById(UUID.fromString(amistadDTO.getUsuarioId2())).get());
        }


        return builder.build();
    }

    @Override
    public AmistadDTO amistadToAmistadDTO(Amistad amistad) {
        AmistadDTO.AmistadDTOBuilder builder = AmistadDTO.builder()
                .id(amistad.getId().toString())
                .usuarioId1(amistad.getUsuario1().getId().toString())
                .usuarioId2(amistad.getUsuario2().getId().toString())
                .estadoAmistad(amistad.getEstadoAmistad().getEstadoAmistad())
                .fechaAmistad(getLocalDate(amistad.getFechaAmistad()));

        return builder.build();
    }

    private String getLocalDate(LocalDate localDate){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return localDate.format(formato);
    }
}
