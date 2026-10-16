package com.exemplo.eventos.repository;

import com.exemplo.eventos.model.Inscricao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    List<Inscricao> findByEventoId(Long eventoId);
}
