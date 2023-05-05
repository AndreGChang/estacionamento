package br.com.uniamerica.estacionamento.service;


import br.com.uniamerica.estacionamento.component.ValidaCPF;
import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class CondutorService {

    @Autowired
    private CondutorRepository condutorRepository;

    @Autowired
    private ValidaCPF validaCPF;

    @Transactional(rollbackFor =  Exception.class)
    public void cadastrar(final Condutor condutor){

        //Verifica o NOME
        Assert.isTrue(condutor.getNome() != null , "Error digite um numero");

       //Verifica o TELEFONE
        Assert.isTrue(condutor.getTelefone() != null, "Error digite uma telefone");
        String regexTelefone = "\\+\\d{2}\\(\\d{3}\\)\\d{5}-\\d{4}";
        Assert.isTrue(!condutor.getTelefone().matches(regexTelefone), "Mascara de telefone invalida");


        //Verificar o CPF
        Assert.isTrue(condutor.getCpf() != null, "CPF, nao informado");
        String regexCpf = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$";
        Assert.isTrue(condutor.getCpf().matches(regexCpf), "Error cpf com mascara errada");

        this.condutorRepository.save(condutor);
    }


    @Transactional
    public void editar(final Condutor condutor){
        final Condutor condutorBanco = this.condutorRepository.findById(condutor.getId()).orElse(null);

        //Verifica o TELEFONE
        Assert.isTrue(condutor.getTelefone() != null, "Error digite uma telefone");

        String regexTelefone = "\\+\\d{2}\\(\\d{3}\\)\\d{5}-\\d{4}";
        Assert.isTrue(!condutor.getTelefone().matches(regexTelefone), "Mascara de telefone invalida");

        //verificar o CPF
        Assert.isTrue(condutorBanco != null || !condutorBanco.getId().equals(condutor.getId()),"1Nao foi possivel identificar o registro");
        Assert.isTrue(condutor.getCpf() != null, "CPF, nao informado");
        Assert.isTrue(condutorRepository.findCpf(condutor.getCpf()).isEmpty(), "CPF ja exixte");

        String regexCpf = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$";
        Assert.isTrue(condutor.getCpf().matches(regexCpf), "Error cpf com mascara errada");



        this.condutorRepository.save(condutor);
    }

    @Transactional
    public void deletar (final Condutor condutor){
        final Condutor condutorBanco = this.condutorRepository.findById(condutor.getId()).orElse(null);

        List<Movimentacao> movimentacaoLista = this.condutorRepository.findCondutor(condutorBanco);

        System.out.println(movimentacaoLista);

        if(movimentacaoLista.isEmpty()) {
            this.condutorRepository.delete(condutorBanco);
        }else{
            condutorBanco.setAtivo(false);
            this.condutorRepository.save(condutor);
        }

    }




}
