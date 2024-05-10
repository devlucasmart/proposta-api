package com.devlucasmart.propostaapi.service;

import com.devlucasmart.propostaapi.comum.exception.ValidacaoException;
import com.devlucasmart.propostaapi.dto.PropostaRequest;
import com.devlucasmart.propostaapi.dto.PropostaResponse;
import com.devlucasmart.propostaapi.entity.Proposta;
import com.devlucasmart.propostaapi.mappers.PropostaMapper;
import com.devlucasmart.propostaapi.repository.PropostaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropostaService {
    private final PropostaRepository repository;
    private final NotificacaoService notificacaoService;

    private final String exchange;

    public PropostaService(PropostaRepository repository,
                           NotificacaoService notificacaoService,
                           @Value("${rabbitmq.propostapendente.exchange}") String exchange) {
        this.repository = repository;
        this.notificacaoService = notificacaoService;
        this.exchange = exchange;
    }

    public List<PropostaResponse> findAll() {
        return PropostaMapper.INSTANCE.toListResponse(repository.findAll());
    }

    public PropostaResponse findById(Long id) {
        var proposta = getById(id);
        return PropostaMapper.INSTANCE.toResponse(proposta);
    }

    public PropostaResponse save(PropostaRequest request) {
        var proposta = PropostaMapper.INSTANCE.toDomain(request);
        repository.save(proposta);
        var response = PropostaMapper.INSTANCE.toResponse(proposta);

        notificacaoService.notificar(response, exchange);
        return response;
    }

    private Proposta getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ValidacaoException("Proposta Não Encontrada"));
    }
}
