package com.exemplo.boliche.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pistas")
public class Pista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numero;

    private Boolean manutencao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Boolean getManutencao() {
        return manutencao;
    }

    public void setManutencao(Boolean manutencao) {
        this.manutencao = manutencao;
    }
}
