package com.skillio.api_v1.service.publicacion;

import com.skillio.api_v1.domain.Publicacion;
import com.skillio.api_v1.models.publicacion.PublicacionDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PublicacionService {
    List<PublicacionDTO> getPublicaciones();

    List<PublicacionDTO> getPublicacionesParaUsuario();

    List<PublicacionDTO> getPublicacionesPorPreferencias(List<String> preferencias);

    List<PublicacionDTO> getPublicacionesPorIdEstudiante(UUID idEstudiante);

    Optional<PublicacionDTO> getPublicacionPorId(UUID idPublicacion);

    Publicacion crearPublicacion(@RequestBody PublicacionDTO publicacionDTO);

    Optional<PublicacionDTO> actualizarPublicacion(UUID idPublicacion, PublicacionDTO publicacionActualizada);

    boolean eliminarPublicacion(UUID idPublicacion);
}
