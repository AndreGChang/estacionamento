package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="marcas", schema = "public")
public class Marca extends AbstractEntity{

    @Getter
    @Setter
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

}
