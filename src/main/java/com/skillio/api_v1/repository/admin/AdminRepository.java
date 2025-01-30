package com.skillio.api_v1.repository.admin;

import com.skillio.api_v1.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {
}
