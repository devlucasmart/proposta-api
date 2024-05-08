package com.devlucasmart.propostaapi.controller;

import com.devlucasmart.propostaapi.dto.PropostaRequest;
import com.devlucasmart.propostaapi.dto.PropostaResponse;
import com.devlucasmart.propostaapi.service.PropostaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("proposta")
public class PropostaController {
    private final PropostaService service;

    @GetMapping
    public ResponseEntity<List<PropostaResponse>> findAll(){
        var responseList = service.findAll();
        return ResponseEntity.ok(responseList);
    }
    @PostMapping
    public ResponseEntity<PropostaResponse> save(@RequestBody PropostaRequest request) {
        var response = service.save(request);
        return ResponseEntity.ok(response);
    }
}
