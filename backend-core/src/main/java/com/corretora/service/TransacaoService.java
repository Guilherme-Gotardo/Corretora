package com.corretora.service;

import com.corretora.domain.TipoMovimentacoes;
import com.corretora.domain.Transacao;
import com.corretora.dto.TransacaoDTO;
import com.corretora.repository.TransacaoRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public Transacao registrarCompra(TransacaoDTO dto) {
        Transacao transacao = fromDTO(dto, TipoMovimentacoes.COMPRA);
        return transacaoRepository.save(transacao);
    }

    public Transacao registrarVenda(TransacaoDTO dto) {
        Transacao transacao = fromDTO(dto, TipoMovimentacoes.VENDA);
        return transacaoRepository.save(transacao);
    }

    private Transacao fromDTO(TransacaoDTO dto, TipoMovimentacoes tipo) {
        return Transacao.builder()
                .usuarioId(new ObjectId(dto.getUsuarioId()))
                .ticker(dto.getTicker())
                .tipo(tipo)
                .quantidade(dto.getQuantidade())
                .precoUnitario(dto.getPrecoUnitario())
                .dataTransacao(LocalDateTime.now())
                .build();
    }

    public List<Transacao> buscarTransacoesPorUsuario(ObjectId usuarioId) {
        return transacaoRepository.findByUsuarioId(usuarioId);
    }
}

