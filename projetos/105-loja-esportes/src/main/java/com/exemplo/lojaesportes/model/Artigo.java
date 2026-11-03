package com.exemplo.lojaesportes.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "artigos")
public class Artigo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String tamanho;

    private BigDecimal preco;

    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "modalidade_id")
    private Modalidade modalidade;
}
