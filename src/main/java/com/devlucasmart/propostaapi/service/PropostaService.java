package com.devlucasmart.propostaapi.service;

import com.devlucasmart.propostaapi.comum.exception.ValidacaoException;
import com.devlucasmart.propostaapi.dto.PropostaRequest;
import com.devlucasmart.propostaapi.dto.PropostaResponse;
import com.devlucasmart.propostaapi.entity.Proposta;
import com.devlucasmart.propostaapi.mappers.PropostaMapper;
import com.devlucasmart.propostaapi.repository.PropostaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropostaService {
    private final PropostaRepository repository;
    private final NotificacaoRabbitService notificacaoService;
    private final SimpMessagingTemplate template;
    private final String exchange;

    public PropostaService(PropostaRepository repository,
                           NotificacaoRabbitService notificacaoService,
                           SimpMessagingTemplate template, @Value("${rabbitmq.propostapendente.exchange}") String exchange) {
        this.repository = repository;
        this.notificacaoService = notificacaoService;
        this.template = template;
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

        notificarRabbitMQ(proposta);
        notificacaoService.notificar(proposta, exchange);
        return PropostaMapper.INSTANCE.toResponse(proposta);
    }

    public void notificarRabbitMQ(Proposta proposta) {
        try {
            notificacaoService.notificar(proposta, exchange);
        } catch (RuntimeException ex) {
            proposta.setIntegrada(false);
            repository.save(proposta);
        }
    }

    private Proposta getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ValidacaoException("Proposta Não Encontrada"));
    }
}
