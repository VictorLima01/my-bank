package com.myBank.MyBank.repository;

import com.myBank.MyBank.entity.Contas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContasRepository extends JpaRepository<Contas, Long> {
}
