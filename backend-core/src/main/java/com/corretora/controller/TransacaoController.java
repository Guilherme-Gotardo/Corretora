package com.corretora.controller;

import com.corretora.domain.Transacao;
import com.corretora.domain.Usuario;
import com.corretora.service.TransacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Transacao>> buscarTransacaoPorUsuarioId(@PathVariable String usuarioId) {
        List<Transacao> transacoes = transacaoService.buscarPorUsuarioId(usuarioId);
        return ResponseEntity.ok(transacoes);
    }

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Transacao>> inserirTransacaoUsuario(
            @PathVariable String usuarioId,
            @RequestBody List<Transacao> transacoes) {
        List<Transacao> transacoesSalvas = transacaoService.salvarTransacao(usuarioId, transacoes);
        return ResponseEntity.status(HttpStatus.CREATED).body(transacoesSalvas);
    }
}
