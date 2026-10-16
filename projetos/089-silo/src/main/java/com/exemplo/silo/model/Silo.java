package com.exemplo.silo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "silos")
public class Silo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Double capacidadeToneladas;

    private String localizacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getCapacidadeToneladas() {
        return capacidadeToneladas;
    }

    public void setCapacidadeToneladas(Double capacidadeToneladas) {
        this.capacidadeToneladas = capacidadeToneladas;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}
