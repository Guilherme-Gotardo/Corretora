package com.corretora.service;

import com.corretora.domain.Transacao;
import com.corretora.repository.TransacaoRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final MongoTemplate mongoTemplate;

    public TransacaoService(TransacaoRepository transacaoRepository, MongoTemplate mongoTemplate) {
        this.transacaoRepository = transacaoRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<Transacao> buscarPorUsuarioId(String usuarioId) {
        return transacaoRepository.findByUsuarioId(usuarioId);
    }

    public List<Transacao> salvarTransacao(String usuarioId, List<Transacao> transacoes) {

        for (Transacao t : transacoes) {
            t.setUsuarioId(usuarioId);
        }

        return transacaoRepository.saveAll(transacoes);
    }
}
