package com.exemplo.agenciaviagens.service;

import com.exemplo.agenciaviagens.model.Contratacao;
import com.exemplo.agenciaviagens.repository.ContratacaoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ContratacaoService {

    private final ContratacaoRepository repository;

    public ContratacaoService(ContratacaoRepository repository) {
        this.repository = repository;
    }

    public List<Contratacao> listar(Long pacoteId, Long clienteId) {
        if (pacoteId != null) {
            return repository.findByPacoteId(pacoteId);
        }
        if (clienteId != null) {
            return repository.findByClienteId(clienteId);
        }
        return repository.findAll();
    }

    public Optional<Contratacao> buscar(Long id) {
        return repository.findById(id);
    }

    public Contratacao criar(Contratacao contratacao) {
        return repository.save(contratacao);
    }

    public Optional<Contratacao> atualizar(Long id, Contratacao contratacao) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        contratacao.setId(id);
        return Optional.of(repository.save(contratacao));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
