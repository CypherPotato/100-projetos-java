package com.exemplo.blog.repository;

import com.exemplo.blog.model.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByAutorId(Long autorId);
}
