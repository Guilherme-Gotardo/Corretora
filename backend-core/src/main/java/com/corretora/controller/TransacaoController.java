package com.corretora.controller;

import com.corretora.domain.Transacao;
import com.corretora.domain.Usuario;
import com.corretora.dto.TransacaoDTO;
import com.corretora.service.TransacaoService;
import org.bson.types.ObjectId;
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

    public ResponseEntity<List<Transacao>> listarTransacoesPorUsuario(
            @PathVariable ObjectId usuarioId
            ) {
        List<Transacao> transacoes = transacaoService.buscarTransacoesPorUsuario(usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(transacoes);
    }
}
