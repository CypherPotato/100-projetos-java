package com.exemplo.escolamusica.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "aulas")
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    private Integer duracaoMin;

    private Boolean presencial;

    @ManyToOne
    @JoinColumn(name = "instrumento_id")
    private Instrumento instrumento;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Integer getDuracaoMin() {
        return duracaoMin;
    }

    public void setDuracaoMin(Integer duracaoMin) {
        this.duracaoMin = duracaoMin;
    }

    public Boolean getPresencial() {
        return presencial;
    }

    public void setPresencial(Boolean presencial) {
        this.presencial = presencial;
    }

    public Instrumento getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(Instrumento instrumento) {
        this.instrumento = instrumento;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}
