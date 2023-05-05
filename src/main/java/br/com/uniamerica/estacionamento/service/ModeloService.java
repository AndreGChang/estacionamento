package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Modelo;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.beans.Transient;

@Service
public class ModeloService {

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Modelo modelo){

    }



}
