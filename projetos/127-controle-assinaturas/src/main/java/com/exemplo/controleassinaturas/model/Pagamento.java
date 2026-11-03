package com.exemplo.controleassinaturas.model;

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
@Table(name = "pagamentos")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataPagamento;

    private BigDecimal valorPago;

    @ManyToOne
    @JoinColumn(name = "assinatura_id")
    private Assinatura assinatura;
}
