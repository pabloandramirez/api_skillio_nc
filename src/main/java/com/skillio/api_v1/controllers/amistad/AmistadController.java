package com.skillio.api_v1.controllers.amistad;

import com.skillio.api_v1.domain.Amistad;
import com.skillio.api_v1.exceptions.NotFoundException;
import com.skillio.api_v1.models.amistad.AmistadDTO;
import com.skillio.api_v1.service.amistad.AmistadService;
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
@RequestMapping("/amistad")
@RequiredArgsConstructor
@Slf4j
public class AmistadController {

    private AmistadService amistadService;

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public List<AmistadDTO> getAmistades(){
        log.info("Muestra todas las amistades");
        return amistadService.getAmistades();
    }

    @GetMapping("/{idAmistad}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public AmistadDTO getAmistadPorId(@PathVariable(name = "idAmistad") UUID idAmistad)
            throws NotFoundException {
        log.info("Buscar amistad por Id");
        return amistadService.getAmistadPorId(idAmistad).orElseThrow(NotFoundException::new);
    }

    @PostMapping(path = "/nuevaAmistad")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public ResponseEntity<Void> crearAmistad(@RequestBody AmistadDTO amistadDTO){
        log.info("Creando una nueva amistad");
        Amistad amistadCreada = amistadService.crearAmistad(amistadDTO);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/" + amistadCreada.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{idAmistad}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public ResponseEntity<Void> actualizarAmistad(@PathVariable(name = "idAmistad") UUID idAmistad,
                                                @RequestBody AmistadDTO amistadDTO) throws NotFoundException {
        Optional<AmistadDTO> amistadDTOOptional = amistadService
                .actualizarAmistad(idAmistad, amistadDTO);
        if (amistadDTOOptional.isEmpty()){
            log.info("Amistad no encontrada");
            throw new NotFoundException();
        } else {
            log.info("Amistad actualizada");
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @DeleteMapping("/{idAmistad}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public ResponseEntity<Void> eliminarAmistad(@PathVariable(name = "idAmistad") UUID idAmistad)
            throws NotFoundException {
        boolean isAmistadEliminada = amistadService.eliminarAmistad(idAmistad);
        if (isAmistadEliminada){
            log.info("Amistad eliminada");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            log.info("Amistad no encontrada");
            throw new NotFoundException();
        }
    }
}
