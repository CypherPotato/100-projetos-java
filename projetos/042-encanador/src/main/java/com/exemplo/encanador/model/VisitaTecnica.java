package com.exemplo.encanador.model;

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
@Table(name = "visitas_tecnicas")
public class VisitaTecnica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricaoProblema;

    private BigDecimal valorMaoDeObra;

    private LocalDate dataVisita;

    private Boolean resolvida;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
