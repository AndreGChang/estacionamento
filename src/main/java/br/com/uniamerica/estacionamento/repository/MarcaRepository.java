package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.uniamerica.estacionamento.entity.Marca;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {

    @Query("from Modelo where marca = :marca")
    public List<Modelo> findModelo (@Param("marca") final Marca marca);

    @Query("from Marca where ativo = true")
    public List<Marca> findMarcasAtivas ();

    @Query("from Marca where nome = :nome AND id != :id")
    public List<Marca> findMarcaNomeEditar(@Param("nome")final String nome, @Param("id")final Long id);

    @Query("from Marca where nome = :nome")
    public List<Marca> findMarcaNomeCadastrar(@Param("nome")final String nome);

}
