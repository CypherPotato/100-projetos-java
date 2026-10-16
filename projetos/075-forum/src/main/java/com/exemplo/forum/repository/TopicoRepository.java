package com.exemplo.forum.repository;

import com.exemplo.forum.model.Topico;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    List<Topico> findByAutorId(Long autorId);
}
