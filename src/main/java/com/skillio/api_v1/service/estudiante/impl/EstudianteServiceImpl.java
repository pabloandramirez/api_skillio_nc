package com.skillio.api_v1.service.estudiante.impl;

import com.skillio.api_v1.config.jwt.JwtUtils;
import com.skillio.api_v1.domain.Estudiante;
import com.skillio.api_v1.mapper.estudiante.EstudianteMapper;
import com.skillio.api_v1.models.estudiante.EstudianteDTO;
import com.skillio.api_v1.repository.estudiante.EstudianteRepository;
import com.skillio.api_v1.service.estudiante.EstudianteService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EstudianteServiceImpl implements EstudianteService {

    private EstudianteRepository estudianteRepository;
    private EstudianteMapper estudianteMapper;
    private PasswordEncoder passwordEncoder;
    private JwtUtils jwtUtils;
    @Override
    public List<EstudianteDTO> getEstudiantes() {
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        return estudianteList.parallelStream()
                .map(estudianteMapper::estudianteToEstudianteDTO)
                .sorted(Comparator.comparing(EstudianteDTO::getFechaRegistro).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EstudianteDTO> getEstudiantesPorId(UUID idEstudiante) {
        Optional<Estudiante> optionalEstudiante = estudianteRepository.findById(idEstudiante);
        return optionalEstudiante.map(estudianteMapper::estudianteToEstudianteDTO);
    }

    @Override
    public Estudiante crearEstudiante(EstudianteDTO estudianteDTO) {
        Estudiante estudiante = estudianteMapper.estudianteDTOtoEstudiante(estudianteDTO);
        return estudianteRepository.save(estudiante);
    }

    @Override
    public Optional<EstudianteDTO> actualizarEstudiante(UUID idEstudiante, EstudianteDTO estudianteActualizado) {
        Optional<Estudiante> estudianteOptional = estudianteRepository.findById(idEstudiante);
        if(estudianteOptional.isPresent()){
            actualizacionEstudiante(estudianteOptional.get(), estudianteActualizado);
            estudianteRepository.saveAndFlush(estudianteOptional.get());
            return Optional.of(estudianteMapper.estudianteToEstudianteDTO(estudianteOptional.get()));
        }
        return Optional.empty();
    }

    @Override
    public boolean borrarEstudiante(UUID idEstudiante) {
        if(estudianteRepository.existsById(idEstudiante)){
            estudianteRepository.deleteById(idEstudiante);
            return true;
        }
        return false;
    }

    @Override
    public Optional<EstudianteDTO> getEstudianteValidadoPorId(UUID idEstudiante, String token) {
        Optional<Estudiante> optionalEstudiante = estudianteRepository.findById(idEstudiante);
        if(optionalEstudiante.isPresent() && Objects.equals(optionalEstudiante.get().getEmail(),
                jwtUtils.getUsernameFromToken(token))){
            return optionalEstudiante.map(estudianteMapper::estudianteToEstudianteValidadoDTO);
        }
        return Optional.empty();
    }

    private void actualizacionEstudiante(Estudiante estudiante, EstudianteDTO estudianteActualizado){

        if (estudianteActualizado.getNombreCompleto() != null && !estudianteActualizado.getNombreCompleto().isBlank()){
            estudiante.setNombreCompleto(estudianteActualizado.getNombreCompleto());
        }

        if (estudianteActualizado.getEmail() != null && !estudianteActualizado.getEmail().isBlank()){
            estudiante.setEmail(estudianteActualizado.getEmail());
        }

        if (estudianteActualizado.getPassword() != null && !estudianteActualizado.getPassword().isBlank()){
            estudiante.setPassword(passwordEncoder.encode(estudianteActualizado.getPassword()));
        }

        if(estudianteActualizado.getBiografia()!=null && !estudianteActualizado.getBiografia().isBlank()){
            estudiante.setBiografia(estudianteActualizado.getBiografia());
        }

        if(estudianteActualizado.getImagenPerfilUrl() != null && !estudianteActualizado.getImagenPerfilUrl().isBlank()){
            estudiante.setImagenPerfilUrl(estudianteActualizado.getImagenPerfilUrl());
        }

        if(estudianteActualizado.getInstitucion() != null && !estudianteActualizado.getInstitucion().isBlank()){
            estudiante.setInstitucion(estudianteActualizado.getInstitucion());
        }

        if(estudianteActualizado.getEducacion() != null && !estudianteActualizado.getEducacion().isBlank()){
            estudiante.setEducacion(estudianteActualizado.getEducacion());
        }

        if(estudianteActualizado.getPreferencias() != null && !estudianteActualizado.getPreferencias().isEmpty()){
            estudiante.setPreferencias(estudianteActualizado.getPreferencias());
        }
    }
}
