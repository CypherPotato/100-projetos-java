package com.exemplo.clinicapsicologia.model;

import jakarta.persistence.*;

@Entity
@Table(name = "psicologos")
public class Psicologo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String crp;

    private String abordagem;

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

    public String getCrp() {
        return crp;
    }

    public void setCrp(String crp) {
        this.crp = crp;
    }

    public String getAbordagem() {
        return abordagem;
    }

    public void setAbordagem(String abordagem) {
        this.abordagem = abordagem;
    }
}
