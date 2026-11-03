package com.exemplo.simuladosconcursos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "simulados")
public class Simulado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private Integer quantidadeQuestoes;

    private Integer duracaoMin;

    private LocalDateTime aplicadoEm;

    @ManyToOne
    @JoinColumn(name = "concurso_id")
    private Concurso concurso;
}
