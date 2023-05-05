package br.com.uniamerica.estacionamento.service;


import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class ConfiguracaoService {

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;


    @Transactional
    public void cadastrar (final Configuracao configuracao){

        Assert.isTrue(configuracao.getValorHora() != null, "Error, campo vazio");
        Assert.isTrue(configuracao.getValorMulta() != null, "Error, campo vazio");
        Assert.isTrue(configuracao.getInicioExpediente() != null, "Error, campo vazio");
        Assert.isTrue(configuracao.getInicioExpediente() != null, "Error, campo vazio");

    }
}
