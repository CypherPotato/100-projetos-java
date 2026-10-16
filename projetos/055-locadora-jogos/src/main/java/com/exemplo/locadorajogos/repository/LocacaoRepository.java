package com.exemplo.locadorajogos.repository;

import com.exemplo.locadorajogos.model.Locacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocacaoRepository extends JpaRepository<Locacao, Long> {

    List<Locacao> findByJogoId(Long jogoId);

    List<Locacao> findByClienteId(Long clienteId);
}
