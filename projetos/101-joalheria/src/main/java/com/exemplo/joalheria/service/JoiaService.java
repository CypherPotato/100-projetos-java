package com.exemplo.joalheria.service;

import com.exemplo.joalheria.model.Joia;
import com.exemplo.joalheria.repository.JoiaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class JoiaService {

    private final JoiaRepository repository;

    public JoiaService(JoiaRepository repository) {
        this.repository = repository;
    }

    public List<Joia> listar(Long categoriaId) {
        if (categoriaId != null) {
            return repository.findByCategoriaId(categoriaId);
        }
        return repository.findAll();
    }

    public Optional<Joia> buscar(Long id) {
        return repository.findById(id);
    }

    public Joia criar(Joia joia) {
        return repository.save(joia);
    }

    public Optional<Joia> atualizar(Long id, Joia joia) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        joia.setId(id);
        return Optional.of(repository.save(joia));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
