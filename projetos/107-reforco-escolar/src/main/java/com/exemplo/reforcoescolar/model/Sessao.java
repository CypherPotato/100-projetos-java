package com.exemplo.reforcoescolar.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sessoes")
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String disciplina;

    private LocalDate dataSessao;

    private Double duracaoHoras;

    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;
}
