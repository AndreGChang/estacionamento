package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.beans.Transient;
import java.util.List;


@Service
public class ModeloService {

    @Autowired
    private ModeloRepository modeloRepository;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Modelo modelo){
        Assert.isTrue(!modelo.getNome().isBlank(),"Error nome vazio");

        Assert.isTrue(modeloRepository.findModeloNomeCadastrar(modelo.getNome()).isEmpty(),"Error nome de modelo ja existe");

        this.modeloRepository.save(modelo);
    }


    @Transactional(rollbackFor = Exception.class)
    public void editar(final Long id,final Modelo modelo){

        final Modelo modeloBanco = this.modeloRepository.findById(id).orElse(null);

        Assert.isTrue(modeloBanco.getId().equals(id) ,"Error id da URL diferente do body");

        Assert.isTrue(modeloBanco != null || !modeloBanco.getId().equals(modelo.getId()),"Registro nao encontrado");

        Assert.isTrue(modeloRepository.findModeloNomeEditar(modelo.getNome(),id).isEmpty(),"Error nome de modelo ja existe");

        this.modeloRepository.save(modelo);

    }


    @Transactional(rollbackFor = Exception.class)
    public void deletar (final Modelo modelo){
        final Modelo modeloBanco = this.modeloRepository.findById(modelo.getId()).orElse(null);

        List<Veiculo> veiculoList = this.modeloRepository.findVeiculo(modeloBanco);
        
        if(veiculoList.isEmpty()){
            this.modeloRepository.delete(modeloBanco);
        }else{
            modeloBanco.setAtivo(false);
            this.modeloRepository.save(modeloBanco);
        }
    }

}
