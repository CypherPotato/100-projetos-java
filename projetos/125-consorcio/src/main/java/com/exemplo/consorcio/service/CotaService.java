package com.exemplo.consorcio.service;

import com.exemplo.consorcio.model.Cota;
import com.exemplo.consorcio.repository.CotaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CotaService {

    private final CotaRepository repository;

    public CotaService(CotaRepository repository) {
        this.repository = repository;
    }

    public List<Cota> listar(Long grupoId) {
        if (grupoId != null) {
            return repository.findByGrupoId(grupoId);
        }
        return repository.findAll();
    }

    public Optional<Cota> buscar(Long id) {
        return repository.findById(id);
    }

    public Cota criar(Cota cota) {
        return repository.save(cota);
    }

    public Optional<Cota> atualizar(Long id, Cota cota) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        cota.setId(id);
        return Optional.of(repository.save(cota));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
