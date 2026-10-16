package com.exemplo.pousada.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "hospedagens")
public class Hospedagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeHospede;

    private String telefone;

    private LocalDate dataChegada;

    private Integer noites;

    @ManyToOne
    @JoinColumn(name = "chale_id")
    private Chale chale;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeHospede() {
        return nomeHospede;
    }

    public void setNomeHospede(String nomeHospede) {
        this.nomeHospede = nomeHospede;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataChegada() {
        return dataChegada;
    }

    public void setDataChegada(LocalDate dataChegada) {
        this.dataChegada = dataChegada;
    }

    public Integer getNoites() {
        return noites;
    }

    public void setNoites(Integer noites) {
        this.noites = noites;
    }

    public Chale getChale() {
        return chale;
    }

    public void setChale(Chale chale) {
        this.chale = chale;
    }
}
