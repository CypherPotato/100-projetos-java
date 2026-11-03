package com.exemplo.controleassinaturas.model;

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
@Table(name = "assinaturas")
public class Assinatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private BigDecimal mensalidade;

    private LocalDate proximaCobranca;

    private Boolean ativa;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
