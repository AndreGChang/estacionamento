package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
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
    @Column(name = "placa", length = 20, nullable = false, unique = true)
    private String placa;

    @Getter
    @Setter
    @Column(name = "ano", nullable = false)
    private int ano;

    //exemplo de enum (nao e pra usar aqui)
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
    @OneToOne
    @JoinColumn(name = "marca", nullable = false)
    private Marca marca;




}
