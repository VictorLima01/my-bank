package com.myBank.MyBank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BodyReverterPagamentosDTO {

    @JsonProperty("idTransacao")
    private Long idTransacao;

}
