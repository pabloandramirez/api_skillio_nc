package com.skillio.api_v1.controllers;

import com.skillio.api_v1.domain.Admin;
import com.skillio.api_v1.exceptions.NotFoundException;
import com.skillio.api_v1.models.admin.AdminDTO;
import com.skillio.api_v1.service.admin.AdminService;
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
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public List<AdminDTO> getAdmins(){
        log.info("Muestra todos los admins");
        return adminService.getAdmins();
    }

    @GetMapping("/{idAdmin}")
    @PreAuthorize("hasRole('ADMIN')")
    public AdminDTO getAdminPorId(@PathVariable(name = "idAdmin") UUID idAdmin)
            throws NotFoundException {
        log.info("Buscar admin por Id");
        return adminService.getAdminPorId(idAdmin).orElseThrow(NotFoundException::new);
    }

    @PostMapping(path = "/nuevoAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> crearAdmin(@RequestBody AdminDTO adminDTO){
        log.info("Creando un nuevo admin");
        Admin adminCreado = adminService.crearAdmin(adminDTO);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/" + adminCreado.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{idAdmin}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> actualizarAdmin(@PathVariable(name = "idAdmin") UUID idAdmin,
                                                     @RequestBody AdminDTO adminDTO) throws NotFoundException {
        Optional<AdminDTO> adminDTOOptional = adminService
                .actualizarAdmin(idAdmin, adminDTO);
        if (adminDTOOptional.isEmpty()){
            log.info("Admin no encontrado");
            throw new NotFoundException();
        } else {
            log.info("Admin actualizado");
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @DeleteMapping("/{idAdmin}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarAdmin(@PathVariable(name = "idAdmin") UUID idAdmin)
            throws NotFoundException {
        boolean isAdminEliminado = adminService.borrarAdmin(idAdmin);
        if (isAdminEliminado){
            log.info("Admin eliminado");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            log.info("Admin no encontrado");
            throw new NotFoundException();
        }
    }
}
