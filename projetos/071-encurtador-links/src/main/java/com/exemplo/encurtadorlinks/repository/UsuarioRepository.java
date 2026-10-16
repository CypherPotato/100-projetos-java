package com.exemplo.encurtadorlinks.repository;

import com.exemplo.encurtadorlinks.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
