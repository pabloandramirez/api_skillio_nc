package com.skillio.api_v1.service.comentario.impl;

import com.skillio.api_v1.domain.Comentario;
import com.skillio.api_v1.mapper.comentario.ComentarioMapper;
import com.skillio.api_v1.models.comentario.ComentarioDTO;
import com.skillio.api_v1.repository.comentario.ComentarioRepository;
import com.skillio.api_v1.repository.estudiante.EstudianteRepository;
import com.skillio.api_v1.repository.publicacion.PublicacionRepository;
import com.skillio.api_v1.repository.usuario.UsuarioRepository;
import com.skillio.api_v1.service.comentario.ComentarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ComentarioServiceImpl implements ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final ComentarioMapper comentarioMapper;
    private final UsuarioRepository usuarioRepository;
    private final PublicacionRepository publicacionRepository;
    private final EstudianteRepository estudianteRepository;

    @Override
    public List<ComentarioDTO> getComentarios() {
        List<Comentario> comentarioList = comentarioRepository.findAll();
        return comentarioList.parallelStream()
                .map(comentarioMapper::comentarioToComentarioDTO)
                .sorted(Comparator.comparing(ComentarioDTO::getFechaPublicacion).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ComentarioDTO> getComentarioPorId(UUID idComentario) {
        Optional<Comentario> optionalComentario = comentarioRepository.findById(idComentario);
        return optionalComentario.map(comentarioMapper::comentarioToComentarioDTO);
    }

    @Override
    public Comentario crearComentario(ComentarioDTO comentarioDTO) {
        Comentario comentario = comentarioMapper.comentarioDTOtoComentario(comentarioDTO);
        return comentarioRepository.save(comentario);
    }

    @Override
    public Optional<ComentarioDTO> actualizarComentario(UUID idComentario, ComentarioDTO comentarioActualizado) {
        Optional<Comentario> comentarioOptional = comentarioRepository.findById(idComentario);
        if(comentarioOptional.isPresent()){
            actualizacionComentario(comentarioOptional.get(), comentarioActualizado);
            comentarioRepository.saveAndFlush(comentarioOptional.get());
            return Optional.of(comentarioMapper.comentarioToComentarioDTO(comentarioOptional.get()));
        }
        return Optional.empty();
    }

    @Override
    public boolean eliminarComentario(UUID idComentario) {
        if(comentarioRepository.existsById(idComentario)){
            comentarioRepository.deleteById(idComentario);
            return true;
        }
        return false;
    }

    private void actualizacionComentario(Comentario comentario, ComentarioDTO comentarioActualizado){
        if (comentarioActualizado.getUsuarioId() != null && !comentarioActualizado.getUsuarioId().isBlank()){
            if (estudianteRepository.findById(UUID.fromString(comentarioActualizado.getUsuarioId())).isPresent()){
                comentario.setEstudiante(estudianteRepository.findById(UUID.fromString(comentarioActualizado.getUsuarioId())).get());
            }
        }

        if (comentarioActualizado.getPublicacionId() != null && !comentarioActualizado.getPublicacionId().isBlank()){
            if (publicacionRepository.findById(UUID.fromString(comentarioActualizado.getPublicacionId())).isPresent()){
                comentario.setPublicacion(publicacionRepository.findById(UUID.fromString(comentarioActualizado.getPublicacionId())).get());
            }
        }

        if (comentarioActualizado.getComentarioTexto() != null && !comentarioActualizado.getComentarioTexto().isBlank()){
            comentario.setComentarioTexto(comentarioActualizado.getComentarioTexto());
            comentario.setFechaComentario(LocalDate.now());
        }
    }
}
