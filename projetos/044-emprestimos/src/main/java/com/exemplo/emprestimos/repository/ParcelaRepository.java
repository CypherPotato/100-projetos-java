package com.exemplo.emprestimos.repository;

import com.exemplo.emprestimos.model.Parcela;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelaRepository extends JpaRepository<Parcela, Long> {

    List<Parcela> findByEmprestimoId(Long emprestimoId);
}
