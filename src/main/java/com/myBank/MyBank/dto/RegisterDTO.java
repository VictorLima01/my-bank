package com.myBank.MyBank.dto;

import com.myBank.MyBank.entity.Contas;

import java.util.List;

public record RegisterDTO(String cpfCnpj, String endereco, String nome, String password, List<Contas> contas, UserRole role) {
    
}
