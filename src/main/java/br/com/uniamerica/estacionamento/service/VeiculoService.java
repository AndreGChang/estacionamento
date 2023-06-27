package br.com.uniamerica.estacionamento.service;


import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
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

    private final String regexPlaca = "^[a-zA-Z]{3}\\-d{4}$";
    private final String regexPlacaNova = "^[A-Za-z]{3}\\d{1}[A-Za-z]{1}\\d{2}$";
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Veiculo veiculo){

        //Assert.isTrue(!veiculo.getPlaca().matches(regexPlaca),"Error a placa esta errada");

        Assert.isTrue( veiculo.getPlaca().length() < 10, "Error placa, valor maximo(10) do campo atingido");

        Assert.isTrue( veiculo.getAno() > 1990 && veiculo.getAno() <= 2023, "Ou esse carro e velo ou e muito novo");

        Assert.isTrue(this.veiculoRepository.findVeiculoPlacaCadastrar(veiculo.getPlaca()).isEmpty(), "Error, essa placa ja existe");

        Assert.isTrue(!veiculo.getPlaca().matches((regexPlaca)) || veiculo.getPlaca().matches(regexPlacaNova), "Placa esta incorreta");
        //DEBUG MANUAL
        System.out.println(veiculo.getPlaca() + veiculo.getId());
        System.out.println(veiculoRepository.findVeiculoPlacaCadastrar(veiculo.getPlaca()));

        this.veiculoRepository.save(veiculo);

    }

    @Transactional(rollbackFor = Exception.class)
    public void editar (final Long id,final Veiculo veiculo){
        final Veiculo veiculoBanco = this.veiculoRepository.findById(veiculo.getId()).orElse(null);

        //Assert.isTrue(!veiculo.getPlaca().matches(regexPlaca),"Error a placa esta errada");

        Assert.isTrue(veiculo.getPlaca().matches((regexPlaca)) || veiculo.getPlaca().matches(regexPlacaNova), "Placa esta incorreta");

        Assert.isTrue(veiculoBanco.getId().equals(id),"Error id da URL diferente do body");

        Assert.isTrue(veiculoBanco != null || !veiculoBanco.getId().equals(veiculo.getId()),"Registro nao identificado");

        Assert.isTrue(this.veiculoRepository.findVeiculoPlacaEditar(veiculo.getPlaca(),id).isEmpty(), "Error, essa placa ja existe");

        Assert.isTrue( veiculo.getPlaca().length() < 10, "Error, valor maximo(10) do campo placa atingido");

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
