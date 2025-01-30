package com.skillio.api_v1.service.admin.impl;

import com.skillio.api_v1.domain.Admin;
import com.skillio.api_v1.mapper.admin.AdminMapper;
import com.skillio.api_v1.models.admin.AdminDTO;
import com.skillio.api_v1.repository.admin.AdminRepository;
import com.skillio.api_v1.service.admin.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;
    private AdminMapper adminMapper;
    private PasswordEncoder passwordEncoder;

    @Override
    public List<AdminDTO> getAdmins() {
        List<Admin> adminList = adminRepository.findAll();
        return adminList.parallelStream()
                .map(adminMapper::adminToAdminDTO)
                .sorted(Comparator.comparing(AdminDTO::getNombreCompleto))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AdminDTO> getAdminPorId(UUID idAdmin) {
        Optional<Admin> optionalAdmin = adminRepository.findById(idAdmin);
        return optionalAdmin.map(adminMapper::adminToAdminDTO);
    }

    @Override
    public Admin crearAdmin(AdminDTO adminDTO) {
        Admin admin = adminMapper.adminDTOtoAdmin(adminDTO);
        return adminRepository.save(admin);
    }

    @Override
    public Optional<AdminDTO> actualizarAdmin(UUID idAdmin, AdminDTO adminActualizado) {
        Optional<Admin> adminOptional = adminRepository.findById(idAdmin);
        if(adminOptional.isPresent()){
            actualizacionAdmin(adminOptional.get(), adminActualizado);
            adminRepository.saveAndFlush(adminOptional.get());
            return Optional.of(adminMapper.adminToAdminDTO(adminOptional.get()));
        }
        return Optional.empty();
    }

    @Override
    public boolean borrarAdmin(UUID idAdmin) {
        if(adminRepository.existsById(idAdmin)){
            adminRepository.deleteById(idAdmin);
            return true;
        }
        return false;
    }

    private void actualizacionAdmin(Admin admin, AdminDTO adminActualizado){

        if (adminActualizado.getNombreCompleto() != null && !adminActualizado.getNombreCompleto().isBlank()){
            admin.setNombreCompleto(adminActualizado.getNombreCompleto());
        }

        if (adminActualizado.getEmail() != null && !adminActualizado.getEmail().isBlank()){
            admin.setEmail(adminActualizado.getEmail());
        }

        if (adminActualizado.getPassword() != null && !adminActualizado.getPassword().isBlank()){
            admin.setPassword(passwordEncoder.encode(adminActualizado.getPassword()));
        }
    }
}
