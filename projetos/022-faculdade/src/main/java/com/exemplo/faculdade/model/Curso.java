package com.exemplo.faculdade.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Integer duracaoSemestres;

    private BigDecimal mensalidade;

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

    public Integer getDuracaoSemestres() {
        return duracaoSemestres;
    }

    public void setDuracaoSemestres(Integer duracaoSemestres) {
        this.duracaoSemestres = duracaoSemestres;
    }

    public BigDecimal getMensalidade() {
        return mensalidade;
    }

    public void setMensalidade(BigDecimal mensalidade) {
        this.mensalidade = mensalidade;
    }
}
