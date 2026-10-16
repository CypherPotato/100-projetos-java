package com.exemplo.lavarapido.service;

import com.exemplo.lavarapido.model.Veiculo;
import com.exemplo.lavarapido.repository.VeiculoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class VeiculoService {

    private final VeiculoRepository repository;

    public VeiculoService(VeiculoRepository repository) {
        this.repository = repository;
    }

    public List<Veiculo> listar() {
        return repository.findAll();
    }

    public Optional<Veiculo> buscar(Long id) {
        return repository.findById(id);
    }

    public Veiculo criar(Veiculo veiculo) {
        return repository.save(veiculo);
    }

    public Optional<Veiculo> atualizar(Long id, Veiculo veiculo) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        veiculo.setId(id);
        return Optional.of(repository.save(veiculo));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
