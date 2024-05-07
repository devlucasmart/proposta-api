package com.devlucasmart.propostaapi.controller;

import com.devlucasmart.propostaapi.dto.PropostaRequest;
import com.devlucasmart.propostaapi.dto.PropostaResponse;
import com.devlucasmart.propostaapi.service.PropostaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("proposta")
public class PropostaController {
    private final PropostaService service;
    @PostMapping
    public ResponseEntity<PropostaResponse> save(@RequestBody PropostaRequest request) {
        var response = service.save(request);
        return ResponseEntity.ok(response);
    }
}
