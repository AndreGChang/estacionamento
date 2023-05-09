package br.com.uniamerica.estacionamento.service;


import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Veiculo veiculo){

        this.veiculoRepository.save(veiculo);

    }

    @Transactional(rollbackFor = Exception.class)
    public void editar (final Long id,final Veiculo veiculo){
        final Veiculo veiculoBanco = this.veiculoRepository.findById(veiculo.getId()).orElse(null);

        Assert.isTrue(veiculoBanco.getId().equals(id),"Error id da URL diferente do body");

        Assert.isTrue(veiculoBanco != null || !veiculoBanco.getId().equals(veiculo.getId()),"Registro nao identificado");

        this.veiculoRepository.save(veiculo);
    }


    @Transactional(rollbackFor = Exception.class)
    public void deletar(final Veiculo veiculo){
        final Veiculo veiculoBanco = this.veiculoRepository.findById(veiculo.getId()).orElse(null);

        List<Movimentacao> movimentacaosLista = this.veiculoRepository.findByVeiculo(veiculoBanco);

        if(movimentacaosLista.isEmpty()){
            this.veiculoRepository.delete(veiculoBanco);
        }else{
            veiculoBanco.setAtivo(false);
            this.veiculoRepository.save(veiculoBanco);
        }
    }
}
