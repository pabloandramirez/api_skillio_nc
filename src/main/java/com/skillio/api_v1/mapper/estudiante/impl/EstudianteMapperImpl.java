package com.skillio.api_v1.mapper.estudiante.impl;

import com.skillio.api_v1.domain.Amistad;
import com.skillio.api_v1.domain.Estudiante;
import com.skillio.api_v1.mapper.estudiante.EstudianteMapper;
import com.skillio.api_v1.models.estudiante.EstudianteDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class EstudianteMapperImpl implements EstudianteMapper {

    private PasswordEncoder passwordEncoder;
    @Override
    public Estudiante estudianteDTOtoEstudiante(EstudianteDTO estudianteDTO) {
        Estudiante.EstudianteBuilder builder = Estudiante.builder()
                .nombreCompleto(estudianteDTO.getNombreCompleto())
                .email(estudianteDTO.getEmail())
                .password(passwordEncoder.encode(estudianteDTO.getPassword()))
                .telefono(estudianteDTO.getTelefono())
                .fechaRegistro(LocalDate.now());

        if(estudianteDTO.getImagenPerfilUrl() != null && !estudianteDTO.getImagenPerfilUrl().isBlank()){
            builder.imagenPerfilUrl(estudianteDTO.getImagenPerfilUrl());
        }

        if(estudianteDTO.getFechaNacimiento()!=null && !estudianteDTO.getFechaNacimiento().isBlank()){
            builder.fechaNacimiento(getLocalDate(estudianteDTO.getFechaNacimiento()));
        }

        if(estudianteDTO.getFechaNacimiento()!= null && !estudianteDTO.getFechaNacimiento().isBlank()){
            builder.institucion(estudianteDTO.getInstitucion());
        }

        if (estudianteDTO.getEducacion()!=null && !estudianteDTO.getEducacion().isBlank()){
            builder.educacion(estudianteDTO.getEducacion());
        }

        if (estudianteDTO.getPreferencias()!=null && !estudianteDTO.getPreferencias().isEmpty()){
            builder.preferencias(estudianteDTO.getPreferencias());
        }

        return builder.build();
    }

    @Override
    public EstudianteDTO estudianteToEstudianteDTO(Estudiante estudiante) {
        EstudianteDTO.EstudianteDTOBuilder builder = EstudianteDTO.builder()
                .id(estudiante.getId().toString())
                .nombreCompleto(estudiante.getNombreCompleto())
                .email(estudiante.getEmail())
                .fechaNacimiento(getLocalDate(estudiante.getFechaNacimiento()))
                .fechaRegistro(getLocalDate(estudiante.getFechaRegistro()));

        if (estudiante.getImagenPerfilUrl() != null && !estudiante.getImagenPerfilUrl().isEmpty()){
            builder.imagenPerfilUrl(estudiante.getImagenPerfilUrl());
        }

        if(estudiante.getInstitucion() != null && !estudiante.getInstitucion().isBlank()){
            builder.institucion(estudiante.getInstitucion());
        }

        if (estudiante.getEducacion() != null && !estudiante.getEducacion().isBlank()){
            builder.educacion(estudiante.getEducacion());
        }

        if(estudiante.getTelefono() != null && !estudiante.getTelefono().toString().isBlank()){
            builder.telefono(String.valueOf(estudiante.getTelefono()));
        }

        if(estudiante.getPreferencias() != null && !estudiante.getPreferencias().isEmpty()){
            builder.preferencias(estudiante.getPreferencias());
        }

        return builder.build();
    }

    @Override
    public EstudianteDTO estudianteToEstudianteValidadoDTO(Estudiante estudiante) {
        EstudianteDTO.EstudianteDTOBuilder builder = EstudianteDTO.builder()
                .id(estudiante.getId().toString())
                .nombreCompleto(estudiante.getNombreCompleto())
                .email(estudiante.getEmail())
                .fechaNacimiento(getLocalDate(estudiante.getFechaNacimiento()))
                .fechaRegistro(getLocalDate(estudiante.getFechaRegistro()));

        if (estudiante.getImagenPerfilUrl() != null && !estudiante.getImagenPerfilUrl().isEmpty()){
            builder.imagenPerfilUrl(estudiante.getImagenPerfilUrl());
        }

        if(estudiante.getInstitucion() != null && !estudiante.getInstitucion().isBlank()){
            builder.institucion(estudiante.getInstitucion());
        }

        if (estudiante.getEducacion() != null && !estudiante.getEducacion().isBlank()){
            builder.educacion(estudiante.getEducacion());
        }

        if(estudiante.getTelefono() != null && !estudiante.getTelefono().toString().isBlank()){
            builder.telefono(String.valueOf(estudiante.getTelefono()));
        }

        if(estudiante.getPreferencias() != null && !estudiante.getPreferencias().isEmpty()){
            builder.preferencias(estudiante.getPreferencias());
        }

        if (estudiante.getAmistadesEnviadas()!=null && !estudiante.getAmistadesEnviadas().isEmpty()){
            builder.amistadesEnviadas(estudiante.getAmistadesEnviadas()
                    .stream()
                    .map(Amistad::toString)
                    .collect(Collectors.toList()));
        }

        if (estudiante.getAmistadesRecibidas()!=null && !estudiante.getAmistadesRecibidas().isEmpty()){
            builder.amistadesRecibidas(estudiante.getAmistadesRecibidas()
                    .stream()
                    .map(Amistad::toString)
                    .collect(Collectors.toList()));
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
