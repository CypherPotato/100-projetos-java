package com.exemplo.controlebackups.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "execucoes")
public class Execucao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    private Double tamanhoMb;

    private Boolean sucesso;

    @ManyToOne
    @JoinColumn(name = "rotina_id")
    private Rotina rotina;
}
