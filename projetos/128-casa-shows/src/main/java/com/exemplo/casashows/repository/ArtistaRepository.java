package com.exemplo.casashows.repository;

import com.exemplo.casashows.model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
}
