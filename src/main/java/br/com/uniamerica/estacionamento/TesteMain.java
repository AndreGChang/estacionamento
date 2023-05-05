package br.com.uniamerica.estacionamento;

import org.springframework.util.Assert;

public class TesteMain {

    public static void main(String[] args){


        System.out.println("teste");

        Assert.isTrue(true,"Error True");

        System.out.println("Sucesso true");

        Assert.isTrue(false, "Error False");

        System.out.println("Sucesso False");
    }
}
