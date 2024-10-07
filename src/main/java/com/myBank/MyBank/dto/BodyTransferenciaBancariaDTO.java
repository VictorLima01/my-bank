package com.myBank.MyBank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BodyTransferenciaBancariaDTO {

    @JsonProperty("agenciaDestinataria")
    private String agenciaDestinataria;

    @JsonProperty("valorTransferencia")
    private Double valorTransferencia;
}
