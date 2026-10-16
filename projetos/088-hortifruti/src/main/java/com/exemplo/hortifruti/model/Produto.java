package com.exemplo.hortifruti.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private BigDecimal precoKg;

    private Double estoqueKg;

    private Boolean organico;

    @ManyToOne
    @JoinColumn(name = "produtor_id")
    private Produtor produtor;
}
