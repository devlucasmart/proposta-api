package com.devlucasmart.propostaapi.dto;

import lombok.Data;

@Data
public class PropostaResponse {
    private Long id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String telefone;
    private Double renda;
    private Double solicitado;
    private int prazoPagamento;
    private Boolean aprovado;
    private String observacao;
}
