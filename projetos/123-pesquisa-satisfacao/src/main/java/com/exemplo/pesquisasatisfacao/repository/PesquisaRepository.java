package com.exemplo.pesquisasatisfacao.repository;

import com.exemplo.pesquisasatisfacao.model.Pesquisa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PesquisaRepository extends JpaRepository<Pesquisa, Long> {
}
