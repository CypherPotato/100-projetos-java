package com.exemplo.clubesocial.service;

import com.exemplo.clubesocial.model.Modalidade;
import com.exemplo.clubesocial.repository.ModalidadeRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ModalidadeService {

    private final ModalidadeRepository repository;

    public ModalidadeService(ModalidadeRepository repository) {
        this.repository = repository;
    }

    public List<Modalidade> listar() {
        return repository.findAll();
    }

    public Optional<Modalidade> buscar(Long id) {
        return repository.findById(id);
    }

    public Modalidade criar(Modalidade modalidade) {
        return repository.save(modalidade);
    }

    public Optional<Modalidade> atualizar(Long id, Modalidade modalidade) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        modalidade.setId(id);
        return Optional.of(repository.save(modalidade));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
