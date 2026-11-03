package com.exemplo.gestaoobras.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "etapas")
public class Etapa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private Boolean concluida;

    private LocalDate prazo;

    @ManyToOne
    @JoinColumn(name = "obra_id")
    private Obra obra;
}
