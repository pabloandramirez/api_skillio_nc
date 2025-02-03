package com.skillio.api_v1.controllers;

import com.skillio.api_v1.domain.Estudiante;
import com.skillio.api_v1.domain.Publicacion;
import com.skillio.api_v1.exceptions.NotFoundException;
import com.skillio.api_v1.models.estudiante.EstudianteDTO;
import com.skillio.api_v1.models.publicacion.PublicacionDTO;
import com.skillio.api_v1.service.estudiante.EstudianteService;
import com.skillio.api_v1.service.publicacion.PublicacionService;
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
@RequestMapping("/publicacion")
@RequiredArgsConstructor
@Slf4j
public class PublicacionController {

    private final PublicacionService publicacionService;

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public List<PublicacionDTO> getPublicaciones(){
        log.info("Muestra todas las publicaciones");
        return publicacionService.getPublicaciones();
    }

    @GetMapping("/buscar")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public List<PublicacionDTO> getPublicacionesPorPreferencias(@RequestParam List<String> preferencias){
        log.info("Muestra todas las publicaciones");
        return publicacionService.getPublicacionesPorPreferencias(preferencias);
    }

    @GetMapping("/{idPublicacion}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public PublicacionDTO getPublicacionPorId(@PathVariable(name = "idPublicacion") UUID idPublicacion)
            throws NotFoundException {
        log.info("Buscar publicacion por Id");
        return publicacionService.getPublicacionPorId(idPublicacion).orElseThrow(NotFoundException::new);
    }

    @PostMapping(path = "/nuevaPublicacion")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public ResponseEntity<Void> crearPublicacion(@RequestBody PublicacionDTO publicacionDTO){
        log.info("Creando una nueva publicacion");
        Publicacion publicacionCreada = publicacionService.crearPublicacion(publicacionDTO);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/" + publicacionCreada.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{idPublicacion}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public ResponseEntity<Void> actualizarPublicacion(@PathVariable(name = "idPublicacion") UUID idPublicacion,
                                                     @RequestBody PublicacionDTO publicacionDTO) throws NotFoundException {
        Optional<PublicacionDTO> publicacionDTOOptional = publicacionService
                .actualizarPublicacion(idPublicacion, publicacionDTO);
        if (publicacionDTOOptional.isEmpty()){
            log.info("Publicacion no encontrada");
            throw new NotFoundException();
        } else {
            log.info("Publicacion actualizada");
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @DeleteMapping("/{idPublicacion}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public ResponseEntity<Void> eliminarPublicacion(@PathVariable(name = "idPublicacion") UUID idPublicacion)
            throws NotFoundException {
        boolean idPublicacionEliminada = publicacionService.eliminarPublicacion(idPublicacion);
        if (idPublicacionEliminada){
            log.info("Publicacion eliminada");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            log.info("Publicacion no encontrada");
            throw new NotFoundException();
        }
    }
}
