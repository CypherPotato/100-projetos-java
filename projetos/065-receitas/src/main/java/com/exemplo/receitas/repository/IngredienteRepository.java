package com.exemplo.receitas.repository;

import com.exemplo.receitas.model.Ingrediente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {

    List<Ingrediente> findByReceitaId(Long receitaId);
}
