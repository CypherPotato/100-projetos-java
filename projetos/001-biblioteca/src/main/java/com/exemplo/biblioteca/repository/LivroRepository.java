package com.exemplo.biblioteca.repository;

import com.exemplo.biblioteca.model.Livro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findByAutorId(Long autorId);
}
