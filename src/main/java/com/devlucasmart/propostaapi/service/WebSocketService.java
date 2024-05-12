package com.devlucasmart.propostaapi.service;

import com.devlucasmart.propostaapi.dto.PropostaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    @Autowired
    private SimpMessagingTemplate template;

    public void notificar(PropostaResponse response) {
        template.convertAndSend("/propostas", response);
    }
}
