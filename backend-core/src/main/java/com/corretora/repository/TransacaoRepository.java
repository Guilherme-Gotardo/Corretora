package com.corretora.repository;

import com.corretora.domain.Transacao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository extends MongoRepository<Transacao, String> {
    List<Transacao> findByUsuarioId(String usuarioId);
}
