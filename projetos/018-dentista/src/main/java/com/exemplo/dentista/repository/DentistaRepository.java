package com.exemplo.dentista.repository;

import com.exemplo.dentista.model.Dentista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DentistaRepository extends JpaRepository<Dentista, Long> {
}
