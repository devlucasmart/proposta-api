package com.devlucasmart.propostaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropostaRequest {
    private String nome;
    private String sobrenome;
    private String cpf;
    private String telefone;
    private Double renda;
    private Double solicitado;
    private int prazoPagamento;
}
