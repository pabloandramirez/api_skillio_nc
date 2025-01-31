package com.skillio.api_v1.service.publicacion.impl;

import com.skillio.api_v1.domain.Publicacion;
import com.skillio.api_v1.enums.Visibilidad;
import com.skillio.api_v1.mapper.publicacion.PublicacionMapper;
import com.skillio.api_v1.models.publicacion.PublicacionDTO;
import com.skillio.api_v1.repository.estudiante.EstudianteRepository;
import com.skillio.api_v1.repository.publicacion.PublicacionRepository;
import com.skillio.api_v1.repository.usuario.UsuarioRepository;
import com.skillio.api_v1.service.publicacion.PublicacionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PublicacionServiceImpl implements PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final PublicacionMapper publicacionMapper;
    private final UsuarioRepository usuarioRepository;
    private final EstudianteRepository estudianteRepository;

    @Override
    public List<PublicacionDTO> getPublicaciones() {
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        return publicacionList.parallelStream()
                .map(publicacionMapper::publicacionToPublicacionDTO)
                .sorted(Comparator.comparing(PublicacionDTO::getFechaPublicacion).reversed())
                .collect(Collectors.toList());
    }

    /*@Override
    public List<PublicacionDTO> getPublicacionesPorPreferencias(List<String> preferencias) {
        List<Publicacion> publicacionList = publicacionRepository.buscarPorPreferencias(preferencias);
        return publicacionList.parallelStream()
                .map(publicacionMapper::publicacionToPublicacionDTO)
                .sorted(Comparator.comparing(PublicacionDTO::getFechaPublicacion).reversed())
                .collect(Collectors.toList());
    }*/

    @Override
    public Optional<PublicacionDTO> getPublicacionPorId(UUID idPublicacion) {
        Optional<Publicacion> optionalPublicacion = publicacionRepository.findById(idPublicacion);
        return optionalPublicacion.map(publicacionMapper::publicacionToPublicacionDTO);
    }

    @Override
    public Publicacion crearPublicacion(PublicacionDTO publicacionDTO) {
        Publicacion publicacion = publicacionMapper.publicacionDTOtoPublicacion(publicacionDTO);
        return publicacionRepository.save(publicacion);
    }

    @Override
    public Optional<PublicacionDTO> actualizarPublicacion(UUID idPublicacion, PublicacionDTO publicacionActualizada) {
        Optional<Publicacion> publicacionOptional = publicacionRepository.findById(idPublicacion);
        if(publicacionOptional.isPresent()){
            actualizacionPublicacion(publicacionOptional.get(), publicacionActualizada);
            publicacionRepository.saveAndFlush(publicacionOptional.get());
            return Optional.of(publicacionMapper.publicacionToPublicacionDTO(publicacionOptional.get()));
        }
        return Optional.empty();
    }

    @Override
    public boolean eliminarPublicacion(UUID idPublicacion) {
        return false;
    }

    private void actualizacionPublicacion(Publicacion publicacion, PublicacionDTO publicacionActualizada){
        if (publicacionActualizada.getUsuarioId() != null && !publicacionActualizada.getUsuarioId().isBlank()){
            if (estudianteRepository.findById(UUID.fromString(publicacionActualizada.getUsuarioId())).isPresent()){
                publicacion.setEstudiante(estudianteRepository.findById(UUID.fromString(publicacionActualizada.getUsuarioId())).get());
            }
        }

        if (publicacionActualizada.getContenido() != null && !publicacionActualizada.getContenido().isBlank()){
            publicacion.setContent(publicacionActualizada.getContenido());
        }

        if (publicacionActualizada.getVisibilidad() != null && !publicacionActualizada.getVisibilidad().isBlank()){
            publicacion.setVisibilidad(Visibilidad.valueOf(publicacionActualizada.getVisibilidad()));
        }
    }
}
