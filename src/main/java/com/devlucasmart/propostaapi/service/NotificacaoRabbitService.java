package com.devlucasmart.propostaapi.service;

import com.devlucasmart.propostaapi.entity.Proposta;
import lombok.RequiredArgsConstructor;

import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificacaoRabbitService {
    private final RabbitTemplate rabbitTemplate;

    public void notificar(Proposta proposta, String exchange, MessagePostProcessor messagePostProcessor) {
        rabbitTemplate.convertAndSend(exchange, "", proposta, messagePostProcessor);
    }

    public void notificar(Proposta proposta, String exchange) {
        rabbitTemplate.convertAndSend(exchange, "", proposta);
    }

}
