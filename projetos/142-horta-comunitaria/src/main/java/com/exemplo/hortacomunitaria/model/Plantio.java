package com.exemplo.hortacomunitaria.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "plantios")
public class Plantio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cultura;

    private LocalDate dataPlantio;

    private Boolean colhido;

    @ManyToOne
    @JoinColumn(name = "canteiro_id")
    private Canteiro canteiro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCultura() {
        return cultura;
    }

    public void setCultura(String cultura) {
        this.cultura = cultura;
    }

    public LocalDate getDataPlantio() {
        return dataPlantio;
    }

    public void setDataPlantio(LocalDate dataPlantio) {
        this.dataPlantio = dataPlantio;
    }

    public Boolean getColhido() {
        return colhido;
    }

    public void setColhido(Boolean colhido) {
        this.colhido = colhido;
    }

    public Canteiro getCanteiro() {
        return canteiro;
    }

    public void setCanteiro(Canteiro canteiro) {
        this.canteiro = canteiro;
    }
}
