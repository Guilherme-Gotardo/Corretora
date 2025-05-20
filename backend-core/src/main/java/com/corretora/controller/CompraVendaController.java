package com.corretora.controller;

import com.corretora.domain.Usuario;
import com.corretora.service.CompraVendaService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/acoes")
public class CompraVendaController {
    private final CompraVendaService compraVendaService;

    public CompraVendaController(CompraVendaService compraVendaService) {
        this.compraVendaService = compraVendaService;
    }

    @PostMapping("/comprar/{usuarioId}")
    public ResponseEntity<String> comprarAcao(
            @PathVariable ObjectId usuarioId,
            @RequestParam String ticker,
            @RequestParam Integer quantidade) {
        String resultado = compraVendaService.comprarAcao(usuarioId, ticker, quantidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @PostMapping("/vender/{usuarioId}")
    public ResponseEntity<String> venderAcao(
            @PathVariable ObjectId usuarioId,
            @RequestParam String ticker,
            @RequestParam Integer quantidade) {
        String resultado = compraVendaService.venderAcao(usuarioId, ticker, quantidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }
}
