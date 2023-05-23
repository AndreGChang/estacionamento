package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;


    @Transactional(rollbackFor = Exception.class)
    public void cadastrar (final Marca marca){
        Assert.isTrue(marca.getNome() != null,"Error nome vazio");

        Assert.isTrue(marcaRepository.findMarcaNomeCadastrar(marca.getNome()).isEmpty(),"Essa marca ja existe");

        this.marcaRepository.save(marca);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Long id,final Marca marca){
        final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);

        Assert.isTrue(marcaBanco.getId().equals(id) ,"Error id da URL diferente do body");

        Assert.isTrue(marcaBanco != null || !marcaBanco.getId().equals(id),"nao foi possivel identificar o registro");

        Assert.isTrue(marcaRepository.findMarcaNomeEditar(marca.getNome(), marca.getId()).isEmpty(),"Essa marca ja existe");

        this.marcaRepository.save(marca);

    }

    @Transactional(rollbackFor = Exception.class)
    public void deletar (final Marca marca){
        final Marca marcaBanco = this.marcaRepository.findById(marca.getId()).orElse(null);

        List<Modelo> modeloLista = this.marcaRepository.findModelo(marcaBanco);

        if(modeloLista.isEmpty()){
            this.marcaRepository.delete(marcaBanco);
            System.out.println("ola1");
        }else{
            marcaBanco.setAtivo(false);
            System.out.println("ola2");
            this.marcaRepository.save(marcaBanco);

        }
    }
}
