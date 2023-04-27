package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="marcas", schema = "public")
@Audited
@AuditTable(value ="marcas_audit", schema= "audit")
public class Marca extends AbstractEntity{

    @Getter
    @Setter
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

}
