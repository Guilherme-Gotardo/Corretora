package com.corretora.controller;

import com.corretora.domain.ContaBancaria;
import com.corretora.dto.ContaBancariaDTO;
import com.corretora.service.ContaBancariaService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conta")
public class ContaBancariaController {
    private final ContaBancariaService contaBancariaService;

    public ContaBancariaController(ContaBancariaService contaBancariaService) {
        this.contaBancariaService = contaBancariaService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ContaBancaria> cadastrarConta(
            @Valid @RequestBody ContaBancariaDTO dto){
        ContaBancaria resultado = contaBancariaService.cadastrarConta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }
}
