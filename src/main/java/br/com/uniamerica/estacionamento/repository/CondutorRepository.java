package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.uniamerica.estacionamento.entity.Condutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface CondutorRepository extends JpaRepository<Condutor, Long> {

    //buscando da forma customizada
    @Query("from Movimentacao where condutor = :condutorid")
    public List<Movimentacao> findCondutor(@Param("condutorid") final Condutor condutorid);


    @Query("from Condutor where cpf = :cpf")
    public List<Condutor> findCpf(@Param("cpf") final String cpf);


}
