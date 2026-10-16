package com.exemplo.lojagames.model;

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
@Table(name = "jogos")
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private BigDecimal preco;

    private LocalDate lancamento;

    private Boolean usado;

    @ManyToOne
    @JoinColumn(name = "plataforma_id")
    private Plataforma plataforma;
}
