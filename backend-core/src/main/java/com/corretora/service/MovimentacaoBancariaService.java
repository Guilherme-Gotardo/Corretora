package com.corretora.service;

import com.corretora.domain.ContaBancaria;
import com.corretora.domain.MovimentacoesBancarias;
import com.corretora.domain.TipoMovimentacoes;
import com.corretora.repository.ContaBancariaRepository;
import com.corretora.repository.MovimentacaoBancariaRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovimentacaoBancariaService {

    private final MovimentacaoBancariaRepository movimentacaoBancariaRepository;
    private final ContaBancariaRepository contaBancariaRepository;

    public String realizarDeposito(ObjectId usuarioId, BigDecimal valor) {
        Optional<ContaBancaria> contaOptional = contaBancariaRepository.findByUsuarioId(usuarioId);

        if (contaOptional.isEmpty()) throw new IllegalArgumentException("Conta não econtrada");

        ContaBancaria conta = contaOptional.get();

        BigDecimal saldoAtual = conta.getSaldoCaixa();
        BigDecimal saldoAtualizado = saldoAtual.add(valor);

        conta.setSaldoCaixa(saldoAtualizado);

        MovimentacoesBancarias deposito = MovimentacoesBancarias.builder()
                .usuarioId(usuarioId)
                .contaBancariaId(conta.getContaId())
                .tipo(TipoMovimentacoes.DEPOSITO)
                .valor(valor)
                .dataMovimentacao(LocalDateTime.now())
                .saldoAnterior(saldoAtual)
                .saldoAtual(saldoAtualizado)
                .build();
        movimentacaoBancariaRepository.save(deposito);
        contaBancariaRepository.save(conta);
        return "Depósito realizado com sucesso.";
    }

    public String realizarSaque(ObjectId usuarioId, BigDecimal valor) {
        Optional<ContaBancaria> contaOptional = contaBancariaRepository.findByUsuarioId(usuarioId);

        if (contaOptional.isEmpty()) throw new IllegalArgumentException("Conta não econtrada");

        ContaBancaria conta = contaOptional.get();

        BigDecimal saldoAtual = conta.getSaldoCaixa();

        if (saldoAtual.compareTo(valor) < 0) throw new IllegalArgumentException("Saldo insuficiente para saque");

        BigDecimal saldoAtualizado = saldoAtual.subtract(valor);

        conta.setSaldoCaixa(saldoAtualizado);

        MovimentacoesBancarias saque = MovimentacoesBancarias.builder()
                .usuarioId(usuarioId)
                .contaBancariaId(conta.getContaId())
                .tipo(TipoMovimentacoes.SAQUE)
                .valor(valor)
                .dataMovimentacao(LocalDateTime.now())
                .saldoAnterior(saldoAtual)
                .saldoAtual(saldoAtualizado)
                .build();
        movimentacaoBancariaRepository.save(saque);
        contaBancariaRepository.save(conta);
        return "Saque realizado com sucesso.";
    }
}
