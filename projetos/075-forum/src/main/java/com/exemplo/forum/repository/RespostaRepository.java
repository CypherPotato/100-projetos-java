package com.exemplo.forum.repository;

import com.exemplo.forum.model.Resposta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {

    List<Resposta> findByTopicoId(Long topicoId);

    List<Resposta> findByAutorId(Long autorId);
}
