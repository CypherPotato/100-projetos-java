package com.exemplo.encanador.service;

import com.exemplo.encanador.model.VisitaTecnica;
import com.exemplo.encanador.repository.VisitaTecnicaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class VisitaTecnicaService {

    private final VisitaTecnicaRepository repository;

    public VisitaTecnicaService(VisitaTecnicaRepository repository) {
        this.repository = repository;
    }

    public List<VisitaTecnica> listar(Long clienteId) {
        if (clienteId != null) {
            return repository.findByClienteId(clienteId);
        }
        return repository.findAll();
    }

    public Optional<VisitaTecnica> buscar(Long id) {
        return repository.findById(id);
    }

    public VisitaTecnica criar(VisitaTecnica visitaTecnica) {
        return repository.save(visitaTecnica);
    }

    public Optional<VisitaTecnica> atualizar(Long id, VisitaTecnica visitaTecnica) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        visitaTecnica.setId(id);
        return Optional.of(repository.save(visitaTecnica));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
