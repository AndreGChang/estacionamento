package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "veiculos", schema = "public")
@Audited
@AuditTable(value= "veiculos_audit", schema = "audit")
public class Veiculo extends AbstractEntity{

    @Getter
    @Setter
    @Column(name = "placa", length = 10, nullable = false, unique = true)
    private String placa;

    @Getter
    @Setter
    @Column(name = "ano", nullable = false)
    //@Min(value = 1990)
    private Integer ano;

    //exemplo de enum
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name = "cor", length = 20, nullable = false)
    private Cor cor;

    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name = "tipo", length = 20, nullable = false)
    private Tipo tipo;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "modelo", nullable = false)
    private Modelo modelo;




}
