package com.exemplo.associacaobairro.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "associados")
public class Associado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Integer numeroCasa;

    private BigDecimal mensalidade;

    private Boolean adimplente;

    @ManyToOne
    @JoinColumn(name = "rua_id")
    private Rua rua;
}
