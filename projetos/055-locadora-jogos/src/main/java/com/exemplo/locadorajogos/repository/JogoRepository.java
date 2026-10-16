package com.exemplo.locadorajogos.repository;

import com.exemplo.locadorajogos.model.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogoRepository extends JpaRepository<Jogo, Long> {
}
