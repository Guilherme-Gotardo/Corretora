package com.corretora.repository;

import com.corretora.domain.MovimentacoesBancarias;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovimentacaoBancariaRepository extends MongoRepository<MovimentacoesBancarias, String> {
}
