package com.myBank.MyBank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "contas", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id", "agencia"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Contas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agencia", unique = true)
    @NotNull(message = "A agência não pode ser nula")
    private String agencia;

    @Column(name = "saldo")
    @DecimalMin(value = "0.0", inclusive = true, message = "O saldo não pode ser negativo")
    private Double saldo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusConta status;

    public enum StatusConta {
        ATIVA,
        INATIVA
    }
}
