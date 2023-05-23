package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {

    @Query("from Veiculo where modelo = :id")
    public List<Veiculo> findVeiculo (@Param("id") final Modelo modelo);

    @Query("from Modelo where ativo = true")
    public List<Modelo> findByAtivo ();

    @Query("from Modelo where nome = :nome")
    public List<Modelo> findModeloNomeCadastrar (@Param("nome")final String nome);

    @Query("from Modelo where nome = :nome AND id != :id")
    public List<Modelo> findModeloNomeEditar (@Param("nome")final String nome, @Param("id")final Long id);

}
