package com.skillio.api_v1.mapper.estudiante.impl;

import com.skillio.api_v1.domain.Estudiante;
import com.skillio.api_v1.mapper.estudiante.EstudianteMapper;
import com.skillio.api_v1.models.estudiante.EstudianteDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
                .telefono(Long.valueOf(estudianteDTO.getTelefono()))
                .fechaNacimiento(getLocalDate(estudianteDTO.getFechaNacimiento()))
                .fechaRegistro(LocalDate.now())
                .institucion(estudianteDTO.getInstitucion())
                .educacion(estudianteDTO.getEducacion())
                .preferencias(estudianteDTO.getPreferencias());

        if(estudianteDTO.getImagenPerfilUrl() != null && !estudianteDTO.getImagenPerfilUrl().isBlank()){
            builder.imagenPerfilUrl(estudianteDTO.getImagenPerfilUrl());
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

    private String getLocalDate(LocalDate localDate){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return localDate.format(formato);
    }

    private LocalDate getLocalDate(String localDate){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(localDate, formato);
    }
}
