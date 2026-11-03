package com.exemplo.gestaoobras.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "materiais")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Integer quantidade;

    private BigDecimal custoUnitario;

    @ManyToOne
    @JoinColumn(name = "obra_id")
    private Obra obra;
}
