package com.exemplo.clubeleitura.repository;

import com.exemplo.clubeleitura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
