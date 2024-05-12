package com.devlucasmart.propostaapi.repository;

import com.devlucasmart.propostaapi.entity.Proposta;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long>, CrudRepository<Proposta, Long> {
    List<Proposta> findAllByIntegradaIsFalse();

    @Transactional
    @Modifying
    @Query(value = "UPDATE proposta SET aprovada = :aprovada, observacao = :observacao WHERE id = :id", nativeQuery = true)
    void atualizarProposta(Long id, boolean aprovada, String observacao);
}
