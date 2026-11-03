package com.exemplo.lojamoveis.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "moveis")
public class Movel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cor;

    private BigDecimal preco;

    private Double largura;

    @ManyToOne
    @JoinColumn(name = "ambiente_id")
    private Ambiente ambiente;
}
