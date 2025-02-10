package com.skillio.api_v1.mapper.estudiante;

import com.skillio.api_v1.domain.Estudiante;
import com.skillio.api_v1.models.estudiante.EstudianteDTO;

public interface EstudianteMapper {

    Estudiante estudianteDTOtoEstudiante(EstudianteDTO estudianteDTO);

    EstudianteDTO estudianteToEstudianteDTO(Estudiante estudiante);

    EstudianteDTO estudianteToEstudianteValidadoDTO(Estudiante estudiante);
}
