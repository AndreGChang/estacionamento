package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.Recibo;
import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    @Autowired
    private CondutorRepository condutorRepository;

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
    public Recibo saida(final Long id){
        final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(id).orElse(null);

        Assert.isTrue(movimentacaoBanco != null, "Error registro nao encontrado");

        movimentacaoBanco.setAtivo(false);

        LocalDateTime saida = LocalDateTime.now();

        Duration duracao = Duration.between(movimentacaoBanco.getEntrada(), saida);

        Configuracao config = this.configuracaoRepository.findById(1L).orElse(null);

        Condutor alguem = this.condutorRepository.findById(movimentacaoBanco.getCondutor().getId()).orElse(null);

        movimentacaoBanco.setSaida(saida);
        //movimentacaoBanco.setHoras(horas);

        final BigDecimal horas = BigDecimal.valueOf(duracao.toHoursPart());
        final BigDecimal minutos = BigDecimal.valueOf(duracao.toMinutesPart()).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_EVEN);
        BigDecimal preco = config.getValorHora().multiply(horas).add(config.getValorHora().multiply(minutos));


        if(config.isGerarDesconto()){
            if(alguem.getTempoDesconto().compareTo(new BigDecimal(config.getTempoParaDesconto())) > 0){
                System.out.println(alguem.getTempoDesconto().compareTo(new BigDecimal(config.getTempoParaDesconto())));
                movimentacaoBanco.setValorDesconto(preco.subtract(config.getTempoDeDesconto()));
            }else{
                alguem.setTempoDesconto(horas.add(minutos));
            }
        }else{
           alguem.setTempoDesconto(horas.add(minutos));
        }

        Integer horasI = horas.intValue();
        Integer minutosI = minutos.intValue();

        alguem.setTempoPago(preco);

        movimentacaoBanco.setHoras(horasI);
        movimentacaoBanco.setMinutos(minutosI);
        movimentacaoBanco.setValorHoraTotal(preco);

        this.movimentacaoRepository.save(movimentacaoBanco);

         return new Recibo(movimentacaoBanco.getEntrada(),
                movimentacaoBanco.getSaida(),
                movimentacaoBanco.getCondutor(),
                movimentacaoBanco.getVeiculo(),
                movimentacaoBanco.getHoras(),
                movimentacaoBanco.getCondutor().getTempoDesconto(),
                movimentacaoBanco.getValorHoraTotal(),
                movimentacaoBanco.getValorDesconto());

    }


}
