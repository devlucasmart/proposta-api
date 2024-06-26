package com.devlucasmart.propostaapi.agendador;

import com.devlucasmart.propostaapi.entity.Proposta;
import com.devlucasmart.propostaapi.repository.PropostaRepository;
import com.devlucasmart.propostaapi.service.NotificacaoRabbitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class PropostaSemIntegracao {

    private final PropostaRepository repository;
    private final NotificacaoRabbitService notificacaoRabbitService;
    private final String exchange;

    private final Logger logger = LoggerFactory.getLogger(PropostaSemIntegracao.class);

    public PropostaSemIntegracao(PropostaRepository repository,
                                 NotificacaoRabbitService notificacaoRabbitService,
                                 @Value("${rabbitmq.propostapendente.exchange}") String exchange) {
        this.repository = repository;
        this.notificacaoRabbitService = notificacaoRabbitService;
        this.exchange = exchange;
    }

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void buscarPropostasSemIntegracao() {
        repository.findAllByIntegradaIsFalse().forEach(proposta -> {
            try {
                notificacaoRabbitService.notificar(proposta, exchange);
                atualizarProposta(proposta);

            } catch (RuntimeException ex) {
                logger.error(ex.getMessage());
            }
        });
    }

    private void atualizarProposta(Proposta proposta) {
        proposta.setIntegrada(true);
        repository.save(proposta);
    }
}
