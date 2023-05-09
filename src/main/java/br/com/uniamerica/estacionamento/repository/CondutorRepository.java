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

    @Query("from Condutor where cpf = :cpf AND id != :id")
    public List<Condutor> findCpf(@Param("cpf") final String cpf,@Param("id")final Long id);

    @Query("from Condutor where telefone = :telefone AND id != :id")
    public List<Condutor> findTelefonesEditar(@Param("telefone")final String telefone, @Param("id")final Long id);

    @Query("from Condutor where telefone = :telefone")
    public List<Condutor> findTelefonesCadastro(@Param("telefone")final String Telefone);

    @Query("from Condutor where cpf = :cpf")
    public List<Condutor> findCpfCadastro(@Param("cpf")final String cpf);

}
