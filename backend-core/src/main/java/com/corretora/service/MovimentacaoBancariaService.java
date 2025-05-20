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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovimentacaoBancariaService {

    private final MovimentacaoBancariaRepository movimentacaoBancariaRepository;
    private final CompraVendaService compraVendaService;
    private final ContaBancariaRepository contaBancariaRepository;

    public String realizarDeposito(ObjectId usuarioId, BigDecimal valor) {
        BigDecimal saldoAtual = compraVendaService.obterSaldoCaixa(usuarioId);
        Optional<ContaBancaria> contaOptional = contaBancariaRepository.findByUsuarioId(usuarioId);

        if (contaOptional.isEmpty()) throw new IllegalArgumentException("Conta não econtrada");

        ContaBancaria conta = contaOptional.get();

        MovimentacoesBancarias deposito = MovimentacoesBancarias.builder()
                .usuarioId(usuarioId)
                .contaBancariaId(conta.getContaId())
                .tipo(TipoMovimentacoes.DEPOSITO)
                .valor(valor)
                .dataMovimentacao(LocalDate.now())
                .saldoAnterior(conta.getSaldoCaixa())
                .saldoAtual(conta.getSaldoCaixa().add(valor))
                .build();
        movimentacaoBancariaRepository.save(deposito);
        return "Depósito realizado com sucesso.";
    }

    public String realizarSaque(ObjectId usuarioId, BigDecimal valor) {

        Optional<ContaBancaria> contaOptional = contaBancariaRepository.findByUsuarioId(usuarioId);
        if (contaOptional.isEmpty()) throw new IllegalArgumentException("Conta não econtrada");

        BigDecimal saldoAtual = compraVendaService.obterSaldoCaixa(usuarioId);
        if (saldoAtual.compareTo(valor) < 0) throw new IllegalArgumentException("Saldo insuficiente para saque");

        ContaBancaria conta = contaOptional.get();

        MovimentacoesBancarias saque = MovimentacoesBancarias.builder()
                .usuarioId(usuarioId)
                .contaBancariaId(conta.getContaId())
                .tipo(TipoMovimentacoes.SAQUE)
                .valor(valor)
                .dataMovimentacao(LocalDate.now())
                .saldoAnterior(conta.getSaldoCaixa())
                .saldoAtual(conta.getSaldoCaixa().subtract(valor))
                .build();
        movimentacaoBancariaRepository.save(saque);
        return "Saque realizado com sucesso.";
    }
}
