package com.exemplo.apicultura.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "colmeias")
public class Colmeia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    private String especie;

    private LocalDate dataInstalacao;

    @ManyToOne
    @JoinColumn(name = "apiario_id")
    private Apiario apiario;
}
