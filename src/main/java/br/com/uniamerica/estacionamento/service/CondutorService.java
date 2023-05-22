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
        Assert.isTrue(!condutor.getTelefone().isBlank(), "Error, campo telefone vazio");
        Assert.isTrue(this.condutorRepository.findTelefonesCadastro(condutor.getTelefone()).isEmpty(),"Telefone ja existe");
        String regexTelefone = "^\\+\\d{2}\\(\\d{3}\\)\\d{5}-\\d{4}$";
        Assert.isTrue(!condutor.getTelefone().matches(regexTelefone), "Mascara de telefone invalida");

        //Verificar o CPF
        Assert.isTrue(this.condutorRepository.findCpfCadastro(condutor.getCpf()).isEmpty(),"Error CPF ja existe");
        Assert.isTrue(!condutor.getCpf().isBlank(), "CPF, nao informado");
        String regexCpf = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$";
        Assert.isTrue(condutor.getCpf().matches(regexCpf), "Error cpf com mascara errada");

        this.condutorRepository.save(condutor);
    }


    @Transactional(rollbackFor =  Exception.class)
    public void editar(final Long id,final Condutor condutor){

        final Condutor condutorBanco = this.condutorRepository.findById(condutor.getId()).orElse(null);
        Assert.isTrue(condutorBanco != null || !condutorBanco.getId().equals(condutor.getId()),"Nao foi possivel identificar o registro");

        Assert.isTrue(condutorBanco.getId().equals(id) ,"Error id da URL diferente do body");

        //Verifica o TELEFONE
        Assert.isTrue(!condutor.getTelefone().isBlank(), "Error digite uma telefone");
        Assert.isTrue(this.condutorRepository.findTelefonesEditar(condutor.getTelefone(), id).isEmpty(),"Error telefone ja existe");

        String regexTelefone = "^\\+\\d{2}\\(\\d{3}\\)\\d{4}-\\d{4}$";
        Assert.isTrue(condutor.getTelefone().matches(regexTelefone), "Mascara de telefone invalida");

        //verificar o CPF
        Assert.isTrue(!condutor.getCpf().isBlank(), "CPF, nao informado");

        Assert.isTrue(this.condutorRepository.findCpf(condutor.getCpf(), id).isEmpty(),"Error cpf ja existe");

        String regexCpf = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$";
        Assert.isTrue(condutor.getCpf().matches(regexCpf), "Error cpf com mascara errada");

        this.condutorRepository.save(condutor);
    }

    @Transactional(rollbackFor =  Exception.class)
    public void deletar (final Long id){

        final Condutor condutorBanco = this.condutorRepository.findById(id).orElse(null);

        Assert.isTrue(condutorBanco != null,"Registro nao encontrado");

        List<Movimentacao> movimentacaoLista = this.condutorRepository.findCondutor(condutorBanco);

        System.out.println(movimentacaoLista);

        if(movimentacaoLista.isEmpty()) {
            this.condutorRepository.delete(condutorBanco);
        }else{
            condutorBanco.setAtivo(false);
            this.condutorRepository.save(condutorBanco);
        }

    }


}
