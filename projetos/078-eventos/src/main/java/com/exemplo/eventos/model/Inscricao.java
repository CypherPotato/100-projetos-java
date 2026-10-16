package com.exemplo.eventos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "inscricoes")
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeParticipante;

    private String email;

    private LocalDateTime dataInscricao;

    private Boolean pago;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;
}
