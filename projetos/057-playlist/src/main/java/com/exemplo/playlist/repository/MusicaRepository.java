package com.exemplo.playlist.repository;

import com.exemplo.playlist.model.Musica;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicaRepository extends JpaRepository<Musica, Long> {

    List<Musica> findByArtistaId(Long artistaId);
}
