package com.devlucasmart.propostaapi.listener;

import com.devlucasmart.propostaapi.entity.Proposta;
import com.devlucasmart.propostaapi.repository.PropostaRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropostaConcluidaListener {

    @Autowired
    private PropostaRepository repository;

    @RabbitListener(queues = "${rabbitmq.queue.proposta.concluida}")
    public void propostaConcluida(Proposta proposta) {
        repository.save(proposta);
    }
}
