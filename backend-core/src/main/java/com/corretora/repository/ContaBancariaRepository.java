package com.corretora.repository;

import com.corretora.domain.Acoes;
import com.corretora.domain.ContaBancaria;
import com.corretora.domain.Usuario;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ContaBancariaRepository extends MongoRepository<ContaBancaria, String> {
    Optional<ContaBancaria> findByUsuarioId(ObjectId usuarioId);
}

