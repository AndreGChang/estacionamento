package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
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

//    @Query("from Movimentacao where condutor = :id")
//    public List<Movimentacao> findCondutor(@Param("id") final Long id);
//


}
