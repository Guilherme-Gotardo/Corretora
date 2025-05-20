package com.corretora.domain;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Document(collection = "movimentacoesBancarias")
public class MovimentacoesBancarias {
    @Id
    private ObjectId usuarioId;

    private String tipo;
    private String ticker;
    private Integer quantidade;
    private BigDecimal valor;
    private LocalDate localDate;
    private BigDecimal saldoAnterior;
    private BigDecimal saldoAtual;

    public MovimentacoesBancarias(ObjectId usuarioId, String tipo, String ticker, Integer quantidade, BigDecimal valor, LocalDate localDate, BigDecimal saldoAnterior, BigDecimal saldoAtual) {
        this.usuarioId = usuarioId;
        this.tipo = tipo;
        this.ticker = ticker;
        this.quantidade = quantidade;
        this.valor = valor;
        this.localDate = localDate;
        this.saldoAnterior = saldoAnterior;
        this.saldoAtual = saldoAtual;
    }
}
