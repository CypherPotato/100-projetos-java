package com.exemplo.granjaavicola.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "coletas")
public class Coleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataColeta;

    private Long ovos;

    private Integer ovosQuebrados;

    @ManyToOne
    @JoinColumn(name = "lote_id")
    private Lote lote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataColeta() {
        return dataColeta;
    }

    public void setDataColeta(LocalDate dataColeta) {
        this.dataColeta = dataColeta;
    }

    public Long getOvos() {
        return ovos;
    }

    public void setOvos(Long ovos) {
        this.ovos = ovos;
    }

    public Integer getOvosQuebrados() {
        return ovosQuebrados;
    }

    public void setOvosQuebrados(Integer ovosQuebrados) {
        this.ovosQuebrados = ovosQuebrados;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }
}
