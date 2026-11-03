package com.exemplo.irrigacao.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "acionamentos")
public class Acionamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime inicio;

    private Integer duracaoMin;

    private Double volumeLitros;

    private Boolean automatico;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public Integer getDuracaoMin() {
        return duracaoMin;
    }

    public void setDuracaoMin(Integer duracaoMin) {
        this.duracaoMin = duracaoMin;
    }

    public Double getVolumeLitros() {
        return volumeLitros;
    }

    public void setVolumeLitros(Double volumeLitros) {
        this.volumeLitros = volumeLitros;
    }

    public Boolean getAutomatico() {
        return automatico;
    }

    public void setAutomatico(Boolean automatico) {
        this.automatico = automatico;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
