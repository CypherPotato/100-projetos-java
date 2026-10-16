package com.exemplo.pousada.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "chales")
public class Chale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Integer capacidade;

    private BigDecimal diaria;

    private Boolean cafeIncluso;

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

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public BigDecimal getDiaria() {
        return diaria;
    }

    public void setDiaria(BigDecimal diaria) {
        this.diaria = diaria;
    }

    public Boolean getCafeIncluso() {
        return cafeIncluso;
    }

    public void setCafeIncluso(Boolean cafeIncluso) {
        this.cafeIncluso = cafeIncluso;
    }
}
