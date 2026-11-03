package com.exemplo.centrodistribuicao.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mercadorias")
public class Mercadoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private Double pesoKg;

    private BigDecimal valorDeclarado;

    @ManyToOne
    @JoinColumn(name = "armazem_id")
    private Armazem armazem;
}
