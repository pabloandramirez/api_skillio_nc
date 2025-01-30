package com.skillio.api_v1.mapper.amistad;

import com.skillio.api_v1.domain.Amistad;
import com.skillio.api_v1.models.amistad.AmistadDTO;

public interface AmistadMapper {

    Amistad amistadDTOtoAmistad(AmistadDTO amistadDTO);

    AmistadDTO amistadToAmistadDTO(Amistad amistad);
}
