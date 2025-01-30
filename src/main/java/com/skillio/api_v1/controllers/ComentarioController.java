package com.skillio.api_v1.controllers;

import com.skillio.api_v1.domain.Comentario;
import com.skillio.api_v1.exceptions.NotFoundException;
import com.skillio.api_v1.models.comentario.ComentarioDTO;
import com.skillio.api_v1.service.comentario.ComentarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/comentario")
@RequiredArgsConstructor
@Slf4j
public class ComentarioController {

    private final ComentarioService comentarioService;

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public List<ComentarioDTO> getComentarios(){
        log.info("Muestra todos los comentarios");
        return comentarioService.getComentarios();
    }

    @GetMapping("/{idComentario}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public ComentarioDTO getComentarioPorId(@PathVariable(name = "idComentario") UUID idComentario)
            throws NotFoundException {
        log.info("Buscar comentario por Id");
        return comentarioService.getComentarioPorId(idComentario).orElseThrow(NotFoundException::new);
    }

    @PostMapping(path = "/nuevoComentario")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public ResponseEntity<Void> crearComentario(@RequestBody ComentarioDTO comentarioDTO){
        log.info("Creando un nuevo comentario");
        Comentario comentarioCreado = comentarioService.crearComentario(comentarioDTO);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/" + comentarioCreado.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{idComentario}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public ResponseEntity<Void> actualizarComentario(@PathVariable(name = "idComentario") UUID idComentario,
                                                     @RequestBody ComentarioDTO comentarioDTO) throws NotFoundException {
        Optional<ComentarioDTO> comentarioDTOOptional = comentarioService
                .actualizarComentario(idComentario, comentarioDTO);
        if (comentarioDTOOptional.isEmpty()){
            log.info("Comentario no encontrado");
            throw new NotFoundException();
        } else {
            log.info("Comentario actualizado");
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @DeleteMapping("/{idComentario}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public ResponseEntity<Void> eliminarComentario(@PathVariable(name = "idComentario") UUID idComentario)
            throws NotFoundException {
        boolean isComentarioEliminado = comentarioService.eliminarComentario(idComentario);
        if (isComentarioEliminado){
            log.info("Comentario eliminado");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            log.info("Comentario no encontrado");
            throw new NotFoundException();
        }
    }
}
