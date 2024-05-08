package com.devlucasmart.propostaapi.service;

import com.devlucasmart.propostaapi.dto.PropostaRequest;
import com.devlucasmart.propostaapi.dto.PropostaResponse;
import com.devlucasmart.propostaapi.entity.Proposta;
import com.devlucasmart.propostaapi.mappers.PropostaMapper;
import com.devlucasmart.propostaapi.repository.PropostaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropostaService {
    private final PropostaRepository repository;

    public List<PropostaResponse> findAll() {
        var propostas = repository.findAll();
        return PropostaMapper.INSTANCE.toListResponse(propostas);
    }

    public PropostaResponse save(PropostaRequest request) {
        var proposta = PropostaMapper.INSTANCE.toDomain(request);
        repository.save(proposta);
        return PropostaMapper.INSTANCE.toResponse(proposta);
    }
}
