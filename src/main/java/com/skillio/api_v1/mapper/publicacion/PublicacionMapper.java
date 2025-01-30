package com.skillio.api_v1.mapper.publicacion;

import com.skillio.api_v1.domain.Publicacion;
import com.skillio.api_v1.models.publicacion.PublicacionDTO;

public interface PublicacionMapper {

    Publicacion publicacionDTOtoPublicacion(PublicacionDTO publicacionDTO);

    PublicacionDTO publicacionToPublicacionDTO(Publicacion publicacion);
}
