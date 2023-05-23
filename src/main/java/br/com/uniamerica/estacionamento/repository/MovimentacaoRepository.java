package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long > {


    @Query("from Movimentacao where saida = null")
    public List<Movimentacao> findSaidas();

    @Query("from Movimentacao where veiculo = :veiculo AND saida = null AND id != :id")
    public List<Movimentacao> findVeiculo(@Param("veiculo")final Veiculo veiculo, @Param("id")final Long id);

    @Query("from Condutor where id = :id")
    public List<Condutor> findCondutorMov(@Param("id")final Long id);

    @Query("from Veiculo where id = :id")
    public List<Veiculo> findVeiculoMov(@Param("id")final Long id);

}
