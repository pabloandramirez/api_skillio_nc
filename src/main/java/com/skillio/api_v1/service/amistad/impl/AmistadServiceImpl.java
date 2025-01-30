package com.skillio.api_v1.service.amistad.impl;

import com.skillio.api_v1.domain.Amistad;
import com.skillio.api_v1.enums.EstadoAmistad;
import com.skillio.api_v1.mapper.amistad.AmistadMapper;
import com.skillio.api_v1.models.amistad.AmistadDTO;
import com.skillio.api_v1.repository.amistad.AmistadRepository;
import com.skillio.api_v1.repository.usuario.UsuarioRepository;
import com.skillio.api_v1.service.amistad.AmistadService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AmistadServiceImpl implements AmistadService {

    private AmistadRepository amistadRepository;
    private AmistadMapper amistadMapper;
    private UsuarioRepository usuarioRepository;

    @Override
    public List<AmistadDTO> getAmistades() {
        List<Amistad> amistadList = amistadRepository.findAll();
        return amistadList.parallelStream()
                .map(amistadMapper::amistadToAmistadDTO)
                .sorted(Comparator.comparing(AmistadDTO::getFechaAmistad).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AmistadDTO> getAmistadPorId(UUID idAmistad) {
        Optional<Amistad> optionalAmistad = amistadRepository.findById(idAmistad);
        return optionalAmistad.map(amistadMapper::amistadToAmistadDTO);
    }

    @Override
    public Amistad crearAmistad(AmistadDTO amistadDTO) {
        Amistad amistad = amistadMapper.amistadDTOtoAmistad(amistadDTO);
        return amistadRepository.save(amistad);
    }

    @Override
    public Optional<AmistadDTO> actualizarAmistad(UUID idAmistad, AmistadDTO amistadActualizada) {
        Optional<Amistad> amistadOptional = amistadRepository.findById(idAmistad);
        if(amistadOptional.isPresent()){
            actualizacionAmistad(amistadOptional.get(), amistadActualizada);
            amistadRepository.saveAndFlush(amistadOptional.get());
            return Optional.of(amistadMapper.amistadToAmistadDTO(amistadOptional.get()));
        }
        return Optional.empty();
    }

    @Override
    public boolean eliminarAmistad(UUID idAmistad) {
        if(amistadRepository.existsById(idAmistad)){
            amistadRepository.deleteById(idAmistad);
            return true;
        }
        return false;
    }

    private void actualizacionAmistad(Amistad amistad, AmistadDTO amistadActualizada){
        if (amistadActualizada.getUsuarioId1() != null && !amistadActualizada.getUsuarioId1().isBlank()){
            if (usuarioRepository.findById(UUID.fromString(amistadActualizada.getUsuarioId1())).isPresent()){
                amistad.setUsuario1(usuarioRepository.findById(UUID.fromString(amistadActualizada.getUsuarioId1())).get());
            }
        }

        if (amistadActualizada.getUsuarioId2() != null && !amistadActualizada.getUsuarioId2().isBlank()){
            if (usuarioRepository.findById(UUID.fromString(amistadActualizada.getUsuarioId2())).isPresent()){
                amistad.setUsuario2(usuarioRepository.findById(UUID.fromString(amistadActualizada.getUsuarioId2())).get());
            }
        }

        if (amistadActualizada.getEstadoAmistad() != null && !amistadActualizada.getEstadoAmistad().isBlank()){
            amistad.setEstadoAmistad(EstadoAmistad.valueOf(amistadActualizada.getEstadoAmistad()));
        }

    }
}
