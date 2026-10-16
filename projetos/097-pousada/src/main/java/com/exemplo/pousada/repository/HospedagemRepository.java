package com.exemplo.pousada.repository;

import com.exemplo.pousada.model.Hospedagem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospedagemRepository extends JpaRepository<Hospedagem, Long> {

    List<Hospedagem> findByChaleId(Long chaleId);
}
