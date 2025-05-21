package com.corretora.service;

import com.corretora.domain.Usuario;
import com.corretora.dto.PerfilInvestimentoQuantidadeDTO;
import com.corretora.dto.UsuarioDTO;
import com.corretora.repository.UsuarioRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final MongoTemplate mongoTemplate;

    public UsuarioService(UsuarioRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    public Usuario criarUsuario(UsuarioDTO dto) {
        Optional<Usuario> usuarioExistente = repository.findByEmail(dto.getEmail());
        if (usuarioExistente.isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        Usuario novoUsuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .perfilInvestidor(dto.getPerfilInvestidor())
                .criadoEm(LocalDate.now())
                .build();
        return repository.save(novoUsuario);
    }

    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    public List<PerfilInvestimentoQuantidadeDTO> agruparPorPerfilInvestimento() {
        Aggregation aggregation = newAggregation(
                group("perfil").count().as("quantidade"),
                project("quantidade").and("perfil").previousOperation()
        );

        AggregationResults<PerfilInvestimentoQuantidadeDTO> results =
                mongoTemplate.aggregate(aggregation, "usuario", PerfilInvestimentoQuantidadeDTO.class);

        return results.getMappedResults();
    }
}








