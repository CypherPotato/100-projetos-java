package com.exemplo.colecaofilmes.repository;

import com.exemplo.colecaofilmes.model.Diretor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiretorRepository extends JpaRepository<Diretor, Long> {
}
