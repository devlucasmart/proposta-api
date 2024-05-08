package com.devlucasmart.propostaapi.repository;

import com.devlucasmart.propostaapi.entity.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long>, CrudRepository<Proposta, Long> {
}
