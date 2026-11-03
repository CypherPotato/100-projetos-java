package com.exemplo.repositoriosnippets.repository;

import com.exemplo.repositoriosnippets.model.Comentario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    List<Comentario> findBySnippetId(Long snippetId);
}
