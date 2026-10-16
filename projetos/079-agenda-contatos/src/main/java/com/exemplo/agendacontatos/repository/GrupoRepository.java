package com.exemplo.agendacontatos.repository;

import com.exemplo.agendacontatos.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
}
