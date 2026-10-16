package com.exemplo.museu.model;

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
@Table(name = "visitas")
public class Visita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String visitante;

    private LocalDate dataVisita;

    private Integer quantidadePessoas;

    private BigDecimal valorTotal;

    @ManyToOne
    @JoinColumn(name = "exposicao_id")
    private Exposicao exposicao;
}
