package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Getter @Setter
    @Column(name = "dtCadastro", nullable = false)
    private LocalDateTime cadastro;

    @Getter @Setter
    @Column(name = "dtAtualizacao")
    private LocalDateTime atualizacao;

    @Getter @Setter
    @Column(name = "ativo", nullable = false)
    private boolean ativo;

    @PrePersist
    private void prePersiste(){
        this.cadastro = LocalDateTime.now();
        this.ativo=true;
    }

    @PreUpdate
    private void preUpdate(){
        this.atualizacao = LocalDateTime.now();
    }





}
