package com.devlucasmart.propostaapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {
    @GetMapping
    public String findAll(){
       return "Usuario Logado";
    }
}
