package br.com.uniamerica.estacionamento.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Table(name = "condutores", schema = "public")
@Audited
@AuditTable(value = "condutores_audit", schema = "audit")
public class Condutor extends AbstractEntity{


    @Getter @Setter
    @Column(name = "nome",nullable = false, length = 100)
    private String nome;

    @Getter @Setter
    @Column(name = "cpf", nullable = false, unique = true,length = 15)
    private String cpf;

    @Getter @Setter
    @Column(name = "telefone", nullable = false, unique = true, length = 18)
    private String telefone;

    @Getter @Setter
    @Column(name = "tempo_gasto")
    private BigDecimal tempoPago;

    @Getter @Setter
    @Column(name = "tempo_desconto")
    private BigDecimal tempoDesconto;


    @PrePersist
    public void prePersist(){
        this.tempoDesconto = new BigDecimal(0);
    }

}
