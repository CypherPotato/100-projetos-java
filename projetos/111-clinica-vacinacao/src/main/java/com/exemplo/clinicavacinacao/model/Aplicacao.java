package com.exemplo.clinicavacinacao.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "aplicacoes")
public class Aplicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paciente;

    private LocalDate dataAplicacao;

    private String lote;

    private Integer dose;

    @ManyToOne
    @JoinColumn(name = "vacina_id")
    private Vacina vacina;
}
