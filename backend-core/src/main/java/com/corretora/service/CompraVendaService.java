package com.corretora.service;

import com.corretora.domain.*;
import com.corretora.dto.TransacaoDTO;
import com.corretora.queries.SistemaQueryService;
import com.corretora.queries.results.QuantidadeAcoesDTO;
import com.corretora.repository.AcoesRepository;
import com.corretora.repository.ContaBancariaRepository;
import com.corretora.repository.MovimentacaoBancariaRepository;
import com.corretora.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
@RequiredArgsConstructor
public class CompraVendaService {

    private final AcoesRepository acoesRepository;
    private final ContaBancariaRepository contaBancariaRepository;
    private final MovimentacaoBancariaRepository movimentacaoBancariaRepository;
    private final TransacaoService transacaoService;
    private final ActionSystemService actionSystemService;

    public String comprarAcao(ObjectId usuarioId, String ticker, Integer quantidade) {
        Optional<Acoes> acaoOptional = acoesRepository.findByTicker(ticker);
        Optional<ContaBancaria> contaOptional = contaBancariaRepository.findByUsuarioId(usuarioId);

        if (acaoOptional.isEmpty()) return "Ação não encontrada.";
        if (contaOptional.isEmpty()) return "Conta bancária não encontrada.";

        Acoes acao = acaoOptional.get();
        ContaBancaria conta = contaOptional.get();

        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade inválida");

        BigDecimal valorTotalCompra = acao.getPreco().multiply(BigDecimal.valueOf(quantidade));
        BigDecimal saldoAtual = conta.getSaldoCaixa();

        if (saldoAtual.compareTo(valorTotalCompra) < 0) {
            return "Saldo insuficiente para compra.";
        }

        BigDecimal saldoAtualizado = conta.getSaldoCaixa().subtract(valorTotalCompra);
        conta.setSaldoCaixa(saldoAtualizado);
        contaBancariaRepository.save(conta);

        MovimentacoesBancarias movimentacao = MovimentacoesBancarias.builder()
                .usuarioId(usuarioId)
                .contaBancariaId(conta.getContaId())
                .tipo(TipoMovimentacoes.COMPRA)
                .ticker(ticker)
                .quantidade(quantidade)
                .valor(valorTotalCompra)
                .dataMovimentacao(LocalDateTime.now())
                .saldoAnterior(saldoAtual)
                .saldoAtual(saldoAtual.subtract(valorTotalCompra))
                .build();
        movimentacaoBancariaRepository.save(movimentacao);

        TransacaoDTO transacaoDTO = TransacaoDTO.builder()
                .usuarioId(usuarioId.toHexString())
                .ticker(ticker)
                .quantidade(quantidade)
                .precoUnitario(acao.getPreco())
                .build();
        transacaoService.registrarCompra(transacaoDTO);
        return "Compra realizada com sucesso.";
    }

    public String venderAcao(ObjectId usuarioId, String ticker, Integer quantidade) {
        Optional<Acoes> acaoOptional = acoesRepository.findByTicker(ticker);
        Optional<ContaBancaria> contaOptional = contaBancariaRepository.findByUsuarioId(usuarioId);

        if (acaoOptional.isEmpty()) return "Ação não encontrada.";
        if (contaOptional.isEmpty()) return "Conta bancária não encontrada.";

        Acoes acao = acaoOptional.get();
        ContaBancaria conta = contaOptional.get();

        QuantidadeAcoesDTO consultaQuantidadeAcoes = actionSystemService.consultaQuantidadeAcoes(usuarioId, ticker);
        Integer quantidadeAtual = consultaQuantidadeAcoes.getQuantidade();

        if (quantidadeAtual < quantidade) {
            return "Você não possui ações suficientes para vender.";
        }

        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade inválida");

        BigDecimal valorTotalVenda = acao.getPreco().multiply(BigDecimal.valueOf(quantidade));
        BigDecimal saldoAtual = conta.getSaldoCaixa();
        BigDecimal saldoAtualizado = saldoAtual.add(valorTotalVenda);
        conta.setSaldoCaixa(saldoAtualizado);
        contaBancariaRepository.save(conta);

        MovimentacoesBancarias movimentacao = MovimentacoesBancarias.builder()
                .usuarioId(usuarioId)
                .contaBancariaId(conta.getContaId())
                .tipo(TipoMovimentacoes.VENDA)
                .ticker(ticker)
                .quantidade(quantidade)
                .valor(valorTotalVenda)
                .dataMovimentacao(LocalDateTime.now())
                .saldoAnterior(saldoAtual)
                .saldoAtual(saldoAtual.add(valorTotalVenda))
                .build();
        movimentacaoBancariaRepository.save(movimentacao);

        TransacaoDTO transacaoDTO = TransacaoDTO.builder()
                .usuarioId(usuarioId.toHexString())
                .ticker(ticker)
                .quantidade(quantidade)
                .precoUnitario(acao.getPreco())
                .build();
        transacaoService.registrarVenda(transacaoDTO);
        return "Venda realizada com sucesso.";
    }
}
