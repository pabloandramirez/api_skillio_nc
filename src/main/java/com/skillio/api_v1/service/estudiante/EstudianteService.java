package com.skillio.api_v1.service.estudiante;

import com.skillio.api_v1.domain.Estudiante;
import com.skillio.api_v1.models.estudiante.EstudianteDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EstudianteService {
    List<EstudianteDTO> getEstudiantes();

    Optional<EstudianteDTO> getEstudiantesPorId(UUID idUsuario);

    Estudiante crearEstudiante(@RequestBody EstudianteDTO estudianteDTO);

    Optional<EstudianteDTO> actualizarEstudiante(UUID idEstudiante, EstudianteDTO estudianteActualizado);

    boolean borrarEstudiante(UUID idEstudiante);

}
