package com.exemplo.clinicaestetica.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "atendimentos")
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cliente;

    private LocalDateTime dataHora;

    private String observacao;

    private Boolean pago;

    @ManyToOne
    @JoinColumn(name = "procedimento_id")
    private Procedimento procedimento;
}
