package br.com.uniamerica.estacionamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.uniamerica.estacionamento.entity.Marca;
public interface MarcaRepository extends JpaRepository<Marca, Long> {

}
