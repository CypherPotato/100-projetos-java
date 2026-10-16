package com.exemplo.brechosolidario.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pecas")
public class Peca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private String tamanho;

    private BigDecimal preco;

    private Boolean vendida;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
