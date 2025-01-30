package com.skillio.api_v1.controllers;


import com.skillio.api_v1.domain.Estudiante;
import com.skillio.api_v1.exceptions.NotFoundException;
import com.skillio.api_v1.models.estudiante.EstudianteDTO;
import com.skillio.api_v1.service.estudiante.EstudianteService;
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
@RequestMapping("/estudiante")
@RequiredArgsConstructor
@Slf4j
public class EstudianteController {

    private final EstudianteService estudianteService;

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public List<EstudianteDTO> getEstudiantes(){
        log.info("Muestra todos los estudiantes");
        return estudianteService.getEstudiantes();
    }

    @GetMapping("/{idEstudiante}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public EstudianteDTO getEstudiantePorId(@PathVariable(name = "idEstudiante") UUID idEstudiante)
            throws NotFoundException {
        log.info("Buscar estudiante por Id");
        return estudianteService.getEstudiantesPorId(idEstudiante).orElseThrow(NotFoundException::new);
    }

    @PostMapping(path = "/nuevoEstudiante")
    public ResponseEntity<Void> crearEstudiante(@RequestBody EstudianteDTO estudianteDTO){
        log.info("Creando un nuevo estudiante");
        Estudiante estudianteCreado = estudianteService.crearEstudiante(estudianteDTO);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/" + estudianteCreado.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{idEstudiante}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public ResponseEntity<Void> actualizarEstudiante(@PathVariable(name = "idEstudiante") UUID idEstudiante,
                                                  @RequestBody EstudianteDTO estudianteDTO) throws NotFoundException {
        Optional<EstudianteDTO> estudianteDTOOptional = estudianteService
                .actualizarEstudiante(idEstudiante, estudianteDTO);
        if (estudianteDTOOptional.isEmpty()){
            log.info("Estudiante no encontrado");
            throw new NotFoundException();
        } else {
            log.info("Estudiante actualizado");
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @DeleteMapping("/{idEstudiante}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public ResponseEntity<Void> borrarEstudiante(@PathVariable(name = "idEstudiante") UUID idEstudiante)
            throws NotFoundException {
        boolean isEstudianteEliminado = estudianteService.borrarEstudiante(idEstudiante);
        if (isEstudianteEliminado){
            log.info("Estudiante eliminado");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            log.info("Estudiante no encontrado");
            throw new NotFoundException();
        }
    }
}
