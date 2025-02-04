package com.skillio.api_v1.service.amistad;

import com.skillio.api_v1.domain.Amistad;
import com.skillio.api_v1.models.amistad.AmistadDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AmistadService {

    List<AmistadDTO> getAmistades();

    Optional<AmistadDTO> getAmistadPorId(UUID idAmistad);

    Amistad crearAmistad(@RequestBody AmistadDTO amistadDTO);

    Optional<AmistadDTO> actualizarAmistad (UUID idAmistad, AmistadDTO amistadActualizada);

    boolean eliminarAmistad (UUID idAmistad);
}
