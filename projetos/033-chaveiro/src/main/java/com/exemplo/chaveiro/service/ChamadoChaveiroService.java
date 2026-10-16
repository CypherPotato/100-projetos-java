package com.exemplo.chaveiro.service;

import com.exemplo.chaveiro.model.ChamadoChaveiro;
import com.exemplo.chaveiro.repository.ChamadoChaveiroRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ChamadoChaveiroService {

    private final ChamadoChaveiroRepository repository;

    public ChamadoChaveiroService(ChamadoChaveiroRepository repository) {
        this.repository = repository;
    }

    public List<ChamadoChaveiro> listar(Long clienteId) {
        if (clienteId != null) {
            return repository.findByClienteId(clienteId);
        }
        return repository.findAll();
    }

    public Optional<ChamadoChaveiro> buscar(Long id) {
        return repository.findById(id);
    }

    public ChamadoChaveiro criar(ChamadoChaveiro chamadoChaveiro) {
        return repository.save(chamadoChaveiro);
    }

    public Optional<ChamadoChaveiro> atualizar(Long id, ChamadoChaveiro chamadoChaveiro) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        chamadoChaveiro.setId(id);
        return Optional.of(repository.save(chamadoChaveiro));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
