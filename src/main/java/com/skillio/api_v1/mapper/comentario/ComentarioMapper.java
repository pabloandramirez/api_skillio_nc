package com.skillio.api_v1.mapper.comentario;

import com.skillio.api_v1.domain.Comentario;
import com.skillio.api_v1.models.comentario.ComentarioDTO;

public interface ComentarioMapper {

    Comentario comentarioDTOtoComentario(ComentarioDTO comentarioDTO);

    ComentarioDTO comentarioToComentarioDTO(Comentario comentario);
}
