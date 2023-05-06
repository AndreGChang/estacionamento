package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {

    @Query("from Veiculo where modelo = :modelo")
    public List<Veiculo> findVeiculo (@RequestParam("id") final Modelo modelo);

    @Query("from Modelo where ativo = true")
    public List<Modelo> findByAtivo ();

}
