package com.exemplo.viveiro.service;

import com.exemplo.viveiro.model.Muda;
import com.exemplo.viveiro.repository.MudaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MudaService {

    private final MudaRepository repository;

    public MudaService(MudaRepository repository) {
        this.repository = repository;
    }

    public List<Muda> listar(Long especieId) {
        if (especieId != null) {
            return repository.findByEspecieId(especieId);
        }
        return repository.findAll();
    }

    public Optional<Muda> buscar(Long id) {
        return repository.findById(id);
    }

    public Muda criar(Muda muda) {
        return repository.save(muda);
    }

    public Optional<Muda> atualizar(Long id, Muda muda) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        muda.setId(id);
        return Optional.of(repository.save(muda));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
