package br.com.uniamerica.estacionamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.uniamerica.estacionamento.entity.Condutor;


public interface CondutorRepository extends JpaRepository<Condutor, Long> {

}
