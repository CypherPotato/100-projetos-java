package com.exemplo.livraria.repository;

import com.exemplo.livraria.model.Livro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findByEditoraId(Long editoraId);
}
