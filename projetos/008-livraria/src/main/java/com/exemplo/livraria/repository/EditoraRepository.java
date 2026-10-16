package com.exemplo.livraria.repository;

import com.exemplo.livraria.model.Editora;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditoraRepository extends JpaRepository<Editora, Long> {
}
