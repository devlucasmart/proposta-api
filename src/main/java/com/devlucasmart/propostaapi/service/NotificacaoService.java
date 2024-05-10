package com.devlucasmart.propostaapi.service;

import com.devlucasmart.propostaapi.dto.PropostaResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificacaoService {
    private final RabbitTemplate rabbitTemplate;

    public void notificar(PropostaResponse response, String exchange) {
        rabbitTemplate.convertAndSend(exchange, "", response);
    }

}
