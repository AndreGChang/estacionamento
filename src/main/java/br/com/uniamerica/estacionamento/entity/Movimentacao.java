package br.com.uniamerica.estacionamento.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name="movimentacoes", schema = "public")
@Audited
@AuditTable(value = "movimentacoes_audit", schema="audit")
public class Movimentacao extends AbstractEntity{


    @Getter
    @Setter
    @Column(name = "entrada", nullable = false)
    private LocalDateTime entrada;

    @Getter
    @Setter
    @Column(name = "saida")
    private LocalDateTime saida;

    @Getter
    @Setter
    @Column(name = "horas")
    private Integer horas;

    @Getter
    @Setter
    @Column(name = "minutos")
    private Integer minutos;

    @Getter
    @Setter
    @Column(name = "tempo_multa")
    private LocalTime tempoMulta;

    @Getter
    @Setter
    @Column(name = "tempo_desconto")
    private Integer tempoDesconto;

    @Getter
    @Setter
    @Column(name = "valor_desconto")
    private BigDecimal valorDesconto;

    @Getter
    @Setter
    @Column(name = "valor_multa")
    private BigDecimal valorMulta;

    @Getter
    @Setter
    @Column(name = "valor_hora_total")
    private BigDecimal valorHoraTotal;

    @Getter
    @Setter
    @Column(name = "valor_hora_multa")
    private BigDecimal valorHoraMulta;

    @Getter
    @Setter
    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @Getter
    @Setter
    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "condutor_id", nullable = false)
    private Condutor condutor;

    @PrePersist
    private void prePersiste(){
        this.setEntrada(LocalDateTime.now());
        this.setTempoDesconto(0);
    }
}
