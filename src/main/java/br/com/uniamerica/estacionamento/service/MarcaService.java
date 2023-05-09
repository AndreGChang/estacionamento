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

        this.marcaRepository.save(marca);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Long id,final Marca marca){
        final Marca marcaBanco = this.marcaRepository.findById(marca.getId()).orElse(null);

        System.out.println(id);
        System.out.println(marcaBanco.getId());

        Assert.isTrue(marcaBanco.getId().equals(id) ,"Error id da URL diferente do body");

        Assert.isTrue(marcaBanco == null || !marcaBanco.getId().equals(marca.getId()),"nao foi possivel identificar o registro");

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
