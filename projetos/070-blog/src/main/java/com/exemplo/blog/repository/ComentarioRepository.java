package com.exemplo.blog.repository;

import com.exemplo.blog.model.Comentario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    List<Comentario> findByPostId(Long postId);
}
