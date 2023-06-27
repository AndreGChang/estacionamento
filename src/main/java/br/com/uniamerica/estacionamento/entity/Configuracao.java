package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;


@Entity
@Table(name="configuracoes", schema = "public")
@Audited
@AuditTable(value = "configuracoes_audit", schema = "audit")
public class Configuracao extends AbstractEntity{

    @Getter
    @Setter
    @Column(name = "valor_hora")
    private BigDecimal valorHora;

    @Getter
    @Setter
    @Column(name = "valor_multa")
    private BigDecimal valorMulta;

    @Getter
    @Setter
    @Column(name = "inicio_expediente")
    private LocalTime inicioExpediente;

    @Getter
    @Setter
    @Column(name = "fim_expediente")
    private LocalTime fimExpediente;

    @Getter
    @Setter
    @Column(name = "tempo_para_desconto")
    private Integer tempoParaDesconto;

    @Getter
    @Setter
    @Column(name = "tempo_desconto")
    private BigDecimal tempoDeDesconto;

    @Getter
    @Setter
    @Column(name = "gerar_desconto")
    private boolean gerarDesconto;

    @Getter
    @Setter
    @Column(name = "vagas_carro")
    private Integer vagasCarro;

    @Getter
    @Setter
    @Column(name = "vagas_moto")
    private Integer vagasMoto;

    @Getter
    @Setter
    @Column(name = "vagas_vans")
    private Integer vagasVans;

//    @PrePersist
//    public void prePersist(){
//        this.valorHora = new BigDecimal(5);
//        this.valorMulta = new BigDecimal(2.5);
//        this.inicioExpediente = ;
//    }
}
