package com.exemplo.helpdesk.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "chamados")
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descricao;

    private String prioridade;

    private LocalDateTime abertoEm;

    private Boolean resolvido;

    @ManyToOne
    @JoinColumn(name = "solicitante_id")
    private Solicitante solicitante;
}
