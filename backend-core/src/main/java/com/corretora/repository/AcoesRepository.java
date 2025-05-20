package com.corretora.repository;

import com.corretora.domain.Acoes;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AcoesRepository extends MongoRepository<Acoes, String> {
    Optional<Acoes> findByTicker(String ticker);
}


