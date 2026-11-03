package com.exemplo.protocolodocumentos.service;

import com.exemplo.protocolodocumentos.model.Documento;
import com.exemplo.protocolodocumentos.repository.DocumentoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DocumentoService {

    private final DocumentoRepository repository;

    public DocumentoService(DocumentoRepository repository) {
        this.repository = repository;
    }

    public List<Documento> listar(Long setorId) {
        if (setorId != null) {
            return repository.findBySetorId(setorId);
        }
        return repository.findAll();
    }

    public Optional<Documento> buscar(Long id) {
        return repository.findById(id);
    }

    public Documento criar(Documento documento) {
        return repository.save(documento);
    }

    public Optional<Documento> atualizar(Long id, Documento documento) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        documento.setId(id);
        return Optional.of(repository.save(documento));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
