package com.myBank.MyBank.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/*
* Classe para indicar ao spring security desablitar autenticações padrões
* e adicionar a config para realizar autenticação stateless
* */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    SecurityFilter securityFilter;


    /***
     * Corrente de filtros da nossa segurança, ou seja, verificar com
     * métodos as chamadas das nossas requisições para verificar se está apto
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return  httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize                        
                        .requestMatchers(HttpMethod.POST, "/myBank/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/myBank/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/myBank/transacao/transferir-para-conta").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/myBank/transacao/reverter-pagamento").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/myBank/transacao/findAll").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
