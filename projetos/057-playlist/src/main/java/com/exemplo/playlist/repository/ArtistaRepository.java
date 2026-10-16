package com.exemplo.playlist.repository;

import com.exemplo.playlist.model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
}
