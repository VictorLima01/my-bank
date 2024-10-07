package com.myBank.MyBank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "transacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agenciaDestinatario", unique = true)
    @NotNull(message = "A agência não pode ser nula")
    private String agenciaDestinatario;

    @Column(name = "agenciaRemetente", unique = true)
    @NotNull(message = "A agência não pode ser nula")
    private String agenciaRemetente;

    @Column(name = "valorTransferencia", unique = true)
    @NotNull(message = "A agência não pode ser nula")
    private Double valorTransferencia;

    private Transacao(Builder builder) {
        this.id = builder.id;
        this.agenciaDestinatario = builder.agenciaDestinatario;
        this.agenciaRemetente = builder.agenciaRemetente;
        this.valorTransferencia = builder.valorTransferencia;
    }


    public static class Builder {
        private Long id;
        private String agenciaDestinatario;
        private String agenciaRemetente;
        private Double valorTransferencia;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withAgenciaDestinatario(String agenciaDestinatario) {
            this.agenciaDestinatario = agenciaDestinatario;
            return this;
        }

        public Builder withAgenciaRemetente(String agenciaRemetente) {
            this.agenciaRemetente = agenciaRemetente;
            return this;
        }

        public Builder withValorTransferencia(Double valorTransferencia) {
            this.valorTransferencia = valorTransferencia;
            return this;
        }

        public Transacao build() {
            return new Transacao(this);
        }
    }

}
