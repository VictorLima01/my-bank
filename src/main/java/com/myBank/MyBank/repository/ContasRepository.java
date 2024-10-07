package com.myBank.MyBank.repository;

import com.myBank.MyBank.entity.Contas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContasRepository extends JpaRepository<Contas, Long> {

    @Query(value = "SELECT * FROM contas WHERE status = 'ATIVA' and agencia = :agencia", nativeQuery = true)
    Contas findAgencia(@Param("agencia") String agencia);
}
