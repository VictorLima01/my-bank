package com.myBank.MyBank.controller;

import com.myBank.MyBank.dto.AuthenticationDTO;
import com.myBank.MyBank.dto.LoginResponseDTO;
import com.myBank.MyBank.dto.RegisterDTO;
import com.myBank.MyBank.entity.Contas;
import com.myBank.MyBank.entity.Users;
import com.myBank.MyBank.infra.security.TokenService;
import com.myBank.MyBank.repository.ContasRepository;
import com.myBank.MyBank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private ContasRepository contasRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Users) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data) {

        if (repository.findByLogin(data.cpfCnpj()) != null) {
            return ResponseEntity.badRequest().body("Usuário já cadastrado.");
        }

        List<Contas> contas = data.contas().stream()
                .map(contaDTO -> new Contas(null, contaDTO.getAgencia(), contaDTO.getSaldo(), contaDTO.getStatus()))
                .toList();

        for (Contas conta : contas) {
            contasRepository.save(conta);
        }

        // Cria uma nova instância de Users
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Users newUser = new Users(
                data.cpfCnpj(),
                data.endereco(),
                data.nome(),
                encryptedPassword,
                contas,
                data.role()
        );

        repository.save(newUser);

        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }
}
