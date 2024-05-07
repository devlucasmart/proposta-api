package com.devlucasmart.propostaapi.service;

import com.devlucasmart.propostaapi.dto.PropostaRequest;
import com.devlucasmart.propostaapi.dto.PropostaResponse;
import com.devlucasmart.propostaapi.repository.PropostaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PropostaService {
    private final PropostaRepository repository;

    public PropostaResponse save(PropostaRequest request) {
        return null;
    }
}
