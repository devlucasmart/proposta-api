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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("proposta")
public class PropostaController {
    private final PropostaService service;

    @GetMapping
    public ResponseEntity<List<PropostaResponse>> findAll() {
        var responseList = service.findAll();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("{id}")
    public ResponseEntity<PropostaResponse> findById(@RequestParam Long id) {
        var response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PropostaResponse> save(@RequestBody PropostaRequest request) {
        var response = service.save(request);

        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(response.getId())
                        .toUri())
                        .body(response);
    }
}
