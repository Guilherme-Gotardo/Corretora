package com.corretora.service;

import com.corretora.domain.ContaBancaria;
import com.corretora.domain.Usuario;
import com.corretora.dto.ContaBancariaDTO;
import com.corretora.dto.UsuarioDTO;
import com.corretora.repository.ContaBancariaRepository;
import com.corretora.repository.UsuarioRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Optional;

public class ContaBancariaService {
    private final ContaBancariaRepository contaBancariaRepository;
    private final UsuarioRepository usuarioRepository;
    private final MongoTemplate mongoTemplate;

    public ContaBancariaService(ContaBancariaRepository contaBancariaRepository, UsuarioRepository usuarioRepository, MongoTemplate mongoTemplate) {
        this.contaBancariaRepository = contaBancariaRepository;
        this.usuarioRepository = usuarioRepository;
        this.mongoTemplate = mongoTemplate;
    }

    // Método de cadastro de conta
    public ContaBancaria cadastrarConta(ContaBancariaDTO dto) {
        ContaBancaria contaBancaria = ContaBancaria.builder()
                .usuarioId(dto.getUsuarioId()).build();

        return contaBancariaRepository.save(contaBancaria);
    }
}
