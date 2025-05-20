package com.corretora.service;

import com.corretora.domain.Acoes;
import com.corretora.domain.ContaBancaria;
import com.corretora.domain.MovimentacoesBancarias;
import com.corretora.repository.AcoesRepository;
import com.corretora.repository.ContaBancariaRepository;
import com.corretora.repository.MovimentacaoBancariaRepository;
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
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
@RequiredArgsConstructor
public class CompraVendaService {

    private final AcoesRepository acoesRepository;
    private final ContaBancariaRepository contaBancariaRepository;
    private final MovimentacaoBancariaRepository movimentacaoBancariaRepository;
    private final MongoTemplate mongoTemplate;

    // Metodo para compras de ações
    public String comprarAcao(ObjectId usuarioId, String ticker, Integer quantidade) {
        Optional<Acoes> acaoOptional = acoesRepository.findByTicker(ticker);
        Optional<ContaBancaria> contaOptional = contaBancariaRepository.findByUsuarioId(usuarioId);

        if (acaoOptional.isEmpty()) return "Ação não encontrada.";
        if (contaOptional.isEmpty()) return "Conta bancária não encontrada.";

        Acoes acao = acaoOptional.get();
        ContaBancaria conta = contaOptional.get();

        BigDecimal valorTotalCompra = acao.getPreco().multiply(BigDecimal.valueOf(quantidade));
        BigDecimal saldoAtual = obterSaldoCaixa(usuarioId);

        if (saldoAtual.compareTo(valorTotalCompra) < 0) {
            return "Saldo insuficiente para compra.";
        }

        MovimentacoesBancarias movimentacao = new MovimentacoesBancarias(
                usuarioId,
                "COMPRA",
                ticker,
                quantidade,
                valorTotalCompra,
                LocalDate.now(),
                saldoAtual,
                saldoAtual.subtract(valorTotalCompra)
        );

        movimentacaoBancariaRepository.save(movimentacao);
        return "Compra realizada com sucesso.";
    }

    // Método para venda de ações
    public String venderAcao(ObjectId usuarioId, String ticker, Integer quantidade) {
        Optional<Acoes> acaoOptional = acoesRepository.findByTicker(ticker);
        Optional<ContaBancaria> contaOptional = contaBancariaRepository.findByUsuarioId(usuarioId);

        if (acaoOptional.isEmpty()) return "Ação não encontrada.";
        if (contaOptional.isEmpty()) return "Conta bancária não encontrada.";

        Acoes acao = acaoOptional.get();
        ContaBancaria conta = contaOptional.get();

        int quantidadeAtual = obterQuantidadeAcoes(usuarioId, ticker);
        if (quantidadeAtual < quantidade) {
            return "Você não possui ações suficientes para vender.";
        }

        BigDecimal valorTotalVenda = acao.getPreco().multiply(BigDecimal.valueOf(quantidade));
        BigDecimal saldoAtual = obterSaldoCaixa(usuarioId);

        MovimentacoesBancarias movimentacao = new MovimentacoesBancarias(
                usuarioId,
                "VENDA",
                ticker,
                quantidade,
                valorTotalVenda,
                LocalDate.now(),
                saldoAtual,
                saldoAtual.add(valorTotalVenda)
        );

        movimentacaoBancariaRepository.save(movimentacao);
        return "Venda realizada com sucesso.";

    }

    // Agregações para fazer as movimentações dos usuarios
    public BigDecimal obterSaldoCaixa(ObjectId usuarioId) {
        Aggregation agg = newAggregation(
                match(org.springframework.data.mongodb.core.query.Criteria.where("usuarioId").is(usuarioId)),
                group()
                        .sum(conditionalSum("tipo", "DEPOSITO", "valor")).as("totalDepositos")
                        .sum(conditionalSum("tipo", "SAQUE", "valor")).as("totalSaques")
                        .sum(conditionalSum("tipo", "COMPRA", "valor")).as("totalCompras")
                        .sum(conditionalSum("tipo", "VENDA", "valor")).as("totalVendas"),
                project()
                        .and(
                                ArithmeticOperators.Subtract.valueOf(
                                        ArithmeticOperators.Add.valueOf("totalDepositos")
                                                .add("totalVendas")
                                ).subtract(
                                        ArithmeticOperators.Add.valueOf("totalSaques")
                                                .add("totalCompras")
                                )
                        ).as("saldoCaixa")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(agg, "movimentacoesBancarias", Document.class);
        Document result = results.getUniqueMappedResult();

        Object saldoCaixaObj = result.get("saldoCaixa");
        if (saldoCaixaObj instanceof Number) {
            return BigDecimal.valueOf(((Number) saldoCaixaObj).doubleValue());
        }
        return BigDecimal.ZERO;
    }

    private int obterQuantidadeAcoes(ObjectId usuarioId, String ticker) {
        Aggregation agg = newAggregation(
                match(org.springframework.data.mongodb.core.query.Criteria.where("usuarioId").is(usuarioId).and("ticker").is(ticker)),
                group("ticker")
                        .sum(conditionalSum("tipo", "COMPRA", "quantidade")).as("compradas")
                        .sum(conditionalSum("tipo", "VENDA", "quantidade")).as("vendidas"),
                project().andExpression("compradas - vendidas").as("quantidadeAtual")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(agg, "movimentacoesBancarias", Document.class);
        Document result = results.getUniqueMappedResult();

        return result != null ? result.getInteger("quantidadeAtual") : 0;
    }

    private org.springframework.data.mongodb.core.aggregation.ConditionalOperators.Cond conditionalSum(String field, String matchValue, String sumField) {
        return org.springframework.data.mongodb.core.aggregation.ConditionalOperators.when(org.springframework.data.mongodb.core.query.Criteria.where(field).is(matchValue))
                .thenValueOf(sumField).otherwise(0);
    }
}
