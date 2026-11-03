package com.exemplo.gestaoobras.service;

import com.exemplo.gestaoobras.model.Material;
import com.exemplo.gestaoobras.repository.MaterialRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MaterialService {

    private final MaterialRepository repository;

    public MaterialService(MaterialRepository repository) {
        this.repository = repository;
    }

    public List<Material> listar(Long obraId) {
        if (obraId != null) {
            return repository.findByObraId(obraId);
        }
        return repository.findAll();
    }

    public Optional<Material> buscar(Long id) {
        return repository.findById(id);
    }

    public Material criar(Material material) {
        return repository.save(material);
    }

    public Optional<Material> atualizar(Long id, Material material) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        material.setId(id);
        return Optional.of(repository.save(material));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
