package com.myBank.MyBank.entity;


import com.myBank.MyBank.dto.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.validation.constraints.NotBlank;

import java.util.Collection;
import java.util.List;

@Entity(name = "USERS")
@Table(name = "users")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Users implements UserDetails {
    @Id
    @Column(name = "cpf_cnpj", unique = true, nullable = false)
    @NotBlank(message = "O campo CPF/CNPJ não pode estar em branco")
    private String cpfCnpj;

    @NotBlank(message = "O campo endereço não pode estar em branco")
    private String endereco;

    @NotBlank(message = "O campo nome não pode estar em branco")
    @Column(name = "nome")
    private String login;

    @NotBlank(message = "O campo senha não pode estar em branco")
    private String password;

    @ManyToMany(targetEntity=Contas.class, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "users_contas", // Nome da tabela de junção
            joinColumns = @JoinColumn(name = "users_cpf_cnpj"), // Chave estrangeira da tabela Users
            inverseJoinColumns = @JoinColumn(name = "conta_id") // Chave estrangeira da tabela Contas
    )
    private List<Contas> contas;

    private UserRole role;


    public Users(String cpfCnpj, String endereco, String login, String password, List<Contas> contas, UserRole role) {
        this.cpfCnpj = cpfCnpj;
        this.endereco = endereco;
        this.login = login;
        this.password = password;
        this.contas = contas;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}