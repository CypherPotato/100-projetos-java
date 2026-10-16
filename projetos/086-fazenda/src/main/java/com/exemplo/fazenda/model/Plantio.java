package com.exemplo.fazenda.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "plantios")
public class Plantio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cultura;

    private LocalDate dataPlantio;

    private LocalDate dataColheitaPrevista;

    private Integer sacasEstimadas;

    @ManyToOne
    @JoinColumn(name = "talhao_id")
    private Talhao talhao;
}
