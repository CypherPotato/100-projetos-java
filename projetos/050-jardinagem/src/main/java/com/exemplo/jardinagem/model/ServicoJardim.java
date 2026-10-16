package com.exemplo.jardinagem.model;

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
@Table(name = "servicos_jardim")
public class ServicoJardim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private Double areaM2;

    private BigDecimal valor;

    private LocalDate dataServico;

    private Boolean recorrente;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
