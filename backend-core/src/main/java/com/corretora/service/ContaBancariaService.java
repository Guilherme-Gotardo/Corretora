package com.corretora.service;

import com.corretora.domain.ContaBancaria;
import com.corretora.domain.MovimentacoesBancarias;
import com.corretora.domain.TipoMovimentacoes;
import com.corretora.domain.Usuario;
import com.corretora.dto.ContaBancariaDTO;
import com.corretora.repository.ContaBancariaRepository;
import com.corretora.repository.UsuarioRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class ContaBancariaService {
    private final ContaBancariaRepository contaBancariaRepository;
    private final UsuarioRepository usuarioRepository;
    private final MongoTemplate mongoTemplate;

    public ContaBancariaService(ContaBancariaRepository contaBancariaRepository, UsuarioRepository usuarioRepository, MongoTemplate mongoTemplate) {
        this.contaBancariaRepository = contaBancariaRepository;
        this.usuarioRepository = usuarioRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public ContaBancaria cadastrarConta(ContaBancariaDTO dto) {
        Optional<Usuario> usuario = usuarioRepository.findById(dto.getUsuarioId().toString());

        if (usuario.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado para o ID: " + dto.getUsuarioId());
        }

        ContaBancaria contaBancaria = ContaBancaria.builder()
                .usuarioId(dto.getUsuarioId()).build();

        ContaBancaria contaSalva =  contaBancariaRepository.save(contaBancaria);

        MovimentacoesBancarias movimentacaoInicial = MovimentacoesBancarias.builder()
                .usuarioId(dto.getUsuarioId())
                .contaBancariaId(contaSalva.getContaId())
                .tipo(TipoMovimentacoes.DEPOSITO)
                .valor(dto.getSaldoInicial())
                .dataMovimentacao(LocalDate.now())
                .saldoAnterior(BigDecimal.ZERO)
                .saldoAtual(dto.getSaldoInicial())
                .build();

        mongoTemplate.save(movimentacaoInicial, "movimentacoesBancarias");

        return contaSalva;
    }
}
