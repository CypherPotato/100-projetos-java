package com.exemplo.fisioterapia.service;

import com.exemplo.fisioterapia.model.Fisioterapeuta;
import com.exemplo.fisioterapia.repository.FisioterapeutaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class FisioterapeutaService {

    private final FisioterapeutaRepository repository;

    public FisioterapeutaService(FisioterapeutaRepository repository) {
        this.repository = repository;
    }

    public List<Fisioterapeuta> listar() {
        return repository.findAll();
    }

    public Optional<Fisioterapeuta> buscar(Long id) {
        return repository.findById(id);
    }

    public Fisioterapeuta criar(Fisioterapeuta fisioterapeuta) {
        return repository.save(fisioterapeuta);
    }

    public Optional<Fisioterapeuta> atualizar(Long id, Fisioterapeuta fisioterapeuta) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        fisioterapeuta.setId(id);
        return Optional.of(repository.save(fisioterapeuta));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
