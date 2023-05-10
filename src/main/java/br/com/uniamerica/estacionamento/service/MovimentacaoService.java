package br.com.uniamerica.estacionamento.service;


import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Movimentacao movimentacao){
        this.movimentacaoRepository.save(movimentacao);
    }

    @Transactional(rollbackFor = Exception.class)
    public List abertas(){
        return this.movimentacaoRepository.findSaidas();
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Long id, final Movimentacao movimentacao){
        final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(movimentacao.getId()).orElse(null);

        Assert.isTrue(movimentacaoBanco.getId().equals(id) ,"Error id da URL diferente do body");

        Assert.isTrue(movimentacaoBanco != null || !movimentacaoBanco.getId().equals(movimentacao.getId()),"Registro nao identificado");

        this.movimentacaoRepository.save(movimentacao);

    }

    @Transactional(rollbackFor = Exception.class)
    public void deletar (final Movimentacao movimentacao) {
        final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(movimentacao.getId()).orElse(null);
        movimentacaoBanco.setAtivo(false);
        this.movimentacaoRepository.save(movimentacaoBanco);

    }

    @Transactional(rollbackFor = Exception.class)
    public void saida(final Long id){
        final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(id).orElse(null);

        Assert.isTrue(movimentacaoBanco != null, "Error registro nao encontrado");

        LocalDateTime saida = LocalDateTime.now();

        Duration duracao = Duration.between(movimentacaoBanco.getEntrada(), saida);

        LocalDateTime diferenca = movimentacaoBanco.getEntrada().plus(duracao);

        LocalTime tempo = diferenca.toLocalTime();

        movimentacaoBanco.setSaida(saida);
        movimentacaoBanco.setTempoTotal(tempo);



    }


}
