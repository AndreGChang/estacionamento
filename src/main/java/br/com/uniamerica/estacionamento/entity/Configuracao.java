package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.math.BigDecimal;
import java.time.LocalTime;


@Entity
@Table(name="configuracoes", schema = "public")
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
    @Column(name = "fim_expiente")
    private LocalTime fimExpediente;

    @Getter
    @Setter
    @Column(name = "tempo_para_desconto")
    private LocalTime tempoParaDesconto;

    @Getter
    @Setter
    @Column(name = "tempo_desconto")
    private LocalTime tempoDeDesconto;

    @Getter
    @Setter
    @Column(name = "gerar_desconto")
    private boolean gerarDesconto;

    @Getter
    @Setter
    @Column(name = "vagas_carro")
    private int vagasCarro;

    @Getter
    @Setter
    @Column(name = "vagas_moto")
    private int vagasMoto;

    @Getter
    @Setter
    @Column(name = "vagas_vans")
    private int vagasVans;


}
