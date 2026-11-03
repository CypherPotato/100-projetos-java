package com.exemplo.protocolodocumentos.service;

import com.exemplo.protocolodocumentos.model.Tramitacao;
import com.exemplo.protocolodocumentos.repository.TramitacaoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TramitacaoService {

    private final TramitacaoRepository repository;

    public TramitacaoService(TramitacaoRepository repository) {
        this.repository = repository;
    }

    public List<Tramitacao> listar(Long documentoId) {
        if (documentoId != null) {
            return repository.findByDocumentoId(documentoId);
        }
        return repository.findAll();
    }

    public Optional<Tramitacao> buscar(Long id) {
        return repository.findById(id);
    }

    public Tramitacao criar(Tramitacao tramitacao) {
        return repository.save(tramitacao);
    }

    public Optional<Tramitacao> atualizar(Long id, Tramitacao tramitacao) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        tramitacao.setId(id);
        return Optional.of(repository.save(tramitacao));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
