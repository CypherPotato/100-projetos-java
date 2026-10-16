package com.exemplo.otica.service;

import com.exemplo.otica.model.Armacao;
import com.exemplo.otica.repository.ArmacaoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ArmacaoService {

    private final ArmacaoRepository repository;

    public ArmacaoService(ArmacaoRepository repository) {
        this.repository = repository;
    }

    public List<Armacao> listar(Long marcaId) {
        if (marcaId != null) {
            return repository.findByMarcaId(marcaId);
        }
        return repository.findAll();
    }

    public Optional<Armacao> buscar(Long id) {
        return repository.findById(id);
    }

    public Armacao criar(Armacao armacao) {
        return repository.save(armacao);
    }

    public Optional<Armacao> atualizar(Long id, Armacao armacao) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        armacao.setId(id);
        return Optional.of(repository.save(armacao));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
