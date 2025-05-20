package com.corretora.service;

import com.corretora.domain.MovimentacoesBancarias;
import com.corretora.repository.MovimentacaoBancariaRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MovimentacaoBancariaService {

    private final MovimentacaoBancariaRepository movimentacaoBancariaRepository;
    private final CompraVendaService compraVendaService; // para usar o método de saldo

    public String realizarDeposito(ObjectId usuarioId, BigDecimal valor) {
        BigDecimal saldoAtual = compraVendaService.obterSaldoCaixa(usuarioId);

        MovimentacoesBancarias deposito = new MovimentacoesBancarias(
                usuarioId,
                "DEPOSITO",
                null,
                null,
                valor,
                LocalDate.now(),
                saldoAtual,
                saldoAtual.add(valor)
        );

        movimentacaoBancariaRepository.save(deposito);
        return "Depósito realizado com sucesso.";
    }

    public String realizarSaque(ObjectId usuarioId, BigDecimal valor) {
        BigDecimal saldoAtual = compraVendaService.obterSaldoCaixa(usuarioId);

        if (saldoAtual.compareTo(valor) < 0) {
            return "Saldo insuficiente para saque.";
        }

        MovimentacoesBancarias saque = new MovimentacoesBancarias(
                usuarioId,
                "SAQUE",
                null,
                null,
                valor,
                LocalDate.now(),
                saldoAtual,
                saldoAtual.subtract(valor)
        );

        movimentacaoBancariaRepository.save(saque);
        return "Saque realizado com sucesso.";
    }
}
