package br.com.uniamerica.estacionamento.service;


import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import jakarta.persistence.RollbackException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class ConfiguracaoService {

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;


    @Transactional(rollbackFor = Exception.class)
    public void cadastrar (final Configuracao configuracao){

        Assert.isTrue(configuracao.getValorHora() != null, "Error, campo vazio");
        Assert.isTrue(configuracao.getValorMulta() != null, "Error, campo vazio");
        Assert.isTrue(configuracao.getInicioExpediente() != null, "Error, campo vazio");
        Assert.isTrue(configuracao.getFimExpediente() != null, "Error, campo vazio");
        Assert.isTrue(configuracao.getTempoParaDesconto() != null, "Error, campo vazio");
        Assert.isTrue(configuracao.getTempoDeDesconto() != null, "Error, campo vazio");

        this.configuracaoRepository.save(configuracao);
    }


    @Transactional(rollbackFor =  Exception.class)
    public void editar(final Long id,final Configuracao configuracao){
        final Configuracao configuracaoBanco = this.configuracaoRepository.findById(configuracao.getId()).orElse(null);

        Assert.isTrue(configuracaoBanco.getId().equals(id) ,"Error id da URL diferente do body");

        Assert.isTrue(configuracaoBanco != null || configuracaoBanco.getId().equals(configuracao.getId()),"Nao foi possivel indetificar o registro");

        this.configuracaoRepository.save(configuracao);
    }
}
