package com.skillio.api_v1.repository.amistad;

import com.skillio.api_v1.domain.Amistad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AmistadRepository extends JpaRepository<Amistad, UUID> {
}
