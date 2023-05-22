package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "modelos", schema = "public")
@Audited
@AuditTable(value = "modelo_audit", schema = "audit")
public class Modelo extends AbstractEntity{

    @Getter
    @Setter
    @Column(name = "nome", length = 100,nullable = false, unique = true)
    private String nome;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "marca",nullable = false)
    private Marca marca;


}
