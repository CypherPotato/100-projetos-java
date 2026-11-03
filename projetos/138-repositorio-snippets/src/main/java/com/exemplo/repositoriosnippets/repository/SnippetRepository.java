package com.exemplo.repositoriosnippets.repository;

import com.exemplo.repositoriosnippets.model.Snippet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnippetRepository extends JpaRepository<Snippet, Long> {

    List<Snippet> findByLinguagemId(Long linguagemId);
}
