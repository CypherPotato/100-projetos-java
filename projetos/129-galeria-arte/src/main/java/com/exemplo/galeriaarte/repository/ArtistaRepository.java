package com.exemplo.galeriaarte.repository;

import com.exemplo.galeriaarte.model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
}
