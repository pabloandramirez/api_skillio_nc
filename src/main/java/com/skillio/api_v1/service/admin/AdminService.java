package com.skillio.api_v1.service.admin;

import com.skillio.api_v1.domain.Admin;
import com.skillio.api_v1.models.admin.AdminDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AdminService {

    List<AdminDTO> getAdmins();

    Optional<AdminDTO> getAdminPorId(UUID idAdmin);

    Admin crearAdmin(@RequestBody AdminDTO adminDTO);

    Optional<AdminDTO> actualizarAdmin (UUID idAdmin, AdminDTO adminActualizado);

    boolean borrarAdmin (UUID idAdmin);
}
