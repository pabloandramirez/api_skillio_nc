package com.skillio.api_v1.repository.publicacion;

import com.skillio.api_v1.domain.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, UUID> {


    @Query("SELECT p FROM Publicacion p JOIN p.palabrasClave pc WHERE pc IN :preferencias")
    List<Publicacion> buscarPorPreferencias(@Param("preferencias") List<String> preferencias);
}
