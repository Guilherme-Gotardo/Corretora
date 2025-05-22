package com.corretora.controller;

import com.corretora.service.MovimentacaoBancariaService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/banco")
public class MovimentacaoBancariaController {

    private final MovimentacaoBancariaService service;

    public MovimentacaoBancariaController(MovimentacaoBancariaService service) {
        this.service = service;
    }

    @PostMapping("/deposito/{usuarioId}")
    public ResponseEntity<String> depositar(
            @PathVariable String usuarioId,
            @RequestParam BigDecimal valor) {
        ObjectId objectId = new ObjectId(usuarioId);        
        String resultado = service.realizarDeposito(objectId, valor);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @PostMapping("/saque/{usuarioId}")
    public ResponseEntity<String> sacar(
            @PathVariable ObjectId usuarioId,
            @RequestParam BigDecimal valor) {
        String resultado = service.realizarSaque(usuarioId, valor);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }
}
