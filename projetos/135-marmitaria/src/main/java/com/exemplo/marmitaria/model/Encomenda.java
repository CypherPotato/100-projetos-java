package com.exemplo.marmitaria.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "encomendas")
public class Encomenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cliente;

    private Integer quantidade;

    private LocalDateTime entrega;

    private Boolean paga;

    @ManyToOne
    @JoinColumn(name = "marmita_id")
    private Marmita marmita;
}
