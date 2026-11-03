package com.exemplo.granjaavicola.model;

import jakarta.persistence.*;

@Entity
@Table(name = "galpoes")
public class Galpao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Integer capacidadeAves;

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

    public Integer getCapacidadeAves() {
        return capacidadeAves;
    }

    public void setCapacidadeAves(Integer capacidadeAves) {
        this.capacidadeAves = capacidadeAves;
    }
}
