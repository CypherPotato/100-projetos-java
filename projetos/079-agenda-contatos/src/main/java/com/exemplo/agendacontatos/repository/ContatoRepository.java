package com.exemplo.agendacontatos.repository;

import com.exemplo.agendacontatos.model.Contato;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contato, Long> {

    List<Contato> findByGrupoId(Long grupoId);
}
