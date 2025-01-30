package com.skillio.api_v1.mapper.admin;

import com.skillio.api_v1.domain.Admin;
import com.skillio.api_v1.models.admin.AdminDTO;

public interface AdminMapper {

    Admin adminDTOtoAdmin(AdminDTO adminDTO);

    AdminDTO adminToAdminDTO(Admin admin);
}
