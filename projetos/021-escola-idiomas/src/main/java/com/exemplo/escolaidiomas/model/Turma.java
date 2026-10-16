package com.exemplo.escolaidiomas.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "turmas")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String horario;

    private Integer vagas;

    private BigDecimal mensalidade;

    @ManyToOne
    @JoinColumn(name = "idioma_id")
    private Idioma idioma;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;
}
