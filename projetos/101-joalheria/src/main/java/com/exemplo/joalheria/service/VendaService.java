package com.exemplo.joalheria.service;

import com.exemplo.joalheria.model.Venda;
import com.exemplo.joalheria.repository.VendaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class VendaService {

    private final VendaRepository repository;

    public VendaService(VendaRepository repository) {
        this.repository = repository;
    }

    public List<Venda> listar(Long joiaId) {
        if (joiaId != null) {
            return repository.findByJoiaId(joiaId);
        }
        return repository.findAll();
    }

    public Optional<Venda> buscar(Long id) {
        return repository.findById(id);
    }

    public Venda criar(Venda venda) {
        return repository.save(venda);
    }

    public Optional<Venda> atualizar(Long id, Venda venda) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        venda.setId(id);
        return Optional.of(repository.save(venda));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
