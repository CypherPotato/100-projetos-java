package com.exemplo.controlevisitantes.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visitas")
public class Visita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String visitante;

    private String documento;

    private LocalDateTime entrada;

    private LocalDateTime saida;

    @ManyToOne
    @JoinColumn(name = "anfitriao_id")
    private Anfitriao anfitriao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVisitante() {
        return visitante;
    }

    public void setVisitante(String visitante) {
        this.visitante = visitante;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }

    public void setEntrada(LocalDateTime entrada) {
        this.entrada = entrada;
    }

    public LocalDateTime getSaida() {
        return saida;
    }

    public void setSaida(LocalDateTime saida) {
        this.saida = saida;
    }

    public Anfitriao getAnfitriao() {
        return anfitriao;
    }

    public void setAnfitriao(Anfitriao anfitriao) {
        this.anfitriao = anfitriao;
    }
}
