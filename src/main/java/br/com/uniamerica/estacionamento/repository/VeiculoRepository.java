package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    @Query("from Movimentacao where veiculo = :veiculo_id")
    public List<Movimentacao> findByVeiculo (@Param("veiculo_id")final Veiculo veiculo_id);

    @Query("from Veiculo where placa = :placa AND id != :id")
    public List<Veiculo> findVeiculoPlacaEditar(@Param("placa") final String placa, @Param("id")final Long id);

    @Query("from Veiculo where placa = :placa")
    public List<Veiculo> findVeiculoPlacaCadastrar(@Param("placa") final String placa);

}
