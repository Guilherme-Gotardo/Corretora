package com.corretora.service;

import com.corretora.queries.SistemaQueryService;
import lombok.RequiredArgsConstructor;
import com.corretora.queries.results.QuantidadeAcoesDTO;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ActionSystemService {
    private final SistemaQueryService sistemaQueryService;

    public QuantidadeAcoesDTO consultaQuantidadeAcoes(ObjectId usuarioId, String ticker) {
        return sistemaQueryService.executarConsulta(
                "quantidade_acoes_usuario",
                Map.of(
                        "usuarioId", usuarioId,
                        "ticker", ticker
                ),
                com.corretora.queries.results.QuantidadeAcoesDTO.class
        );
    }
}
