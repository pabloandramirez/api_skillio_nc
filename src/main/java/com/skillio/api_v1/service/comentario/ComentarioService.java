package com.skillio.api_v1.service.comentario;

import com.skillio.api_v1.domain.Comentario;
import com.skillio.api_v1.models.comentario.ComentarioDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ComentarioService {

    List<ComentarioDTO> getComentarios();

    Optional<ComentarioDTO> getComentarioPorId(UUID idComentario);

    Comentario crearComentario(@RequestBody ComentarioDTO comentarioDTO);

    Optional<ComentarioDTO> actualizarComentario(UUID idComentario, ComentarioDTO comentarioActualizado);

    boolean eliminarComentario(UUID idComentario);
}
