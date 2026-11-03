package com.exemplo.escoteiros.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "escoteiros")
public class Escoteiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private LocalDate dataNascimento;

    private String insignia;

    private Boolean ativo;

    @ManyToOne
    @JoinColumn(name = "patrulha_id")
    private Patrulha patrulha;
}
