package com.exemplo.investimentos.repository;

import com.exemplo.investimentos.model.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
}
