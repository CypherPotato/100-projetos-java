package com.exemplo.inventarioti.repository;

import com.exemplo.inventarioti.model.Equipamento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {

    List<Equipamento> findBySetorId(Long setorId);
}
