package com.myBank.MyBank.repository;

import com.myBank.MyBank.entity.Contas;
import com.myBank.MyBank.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, String> {

    UserDetails findByLogin(String login);

    @Query(value = "SELECT * FROM users WHERE nome = :login", nativeQuery = true)
    Users findUserLogado(@Param("login") String login);

}
