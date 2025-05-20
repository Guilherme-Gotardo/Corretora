package com.corretora.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Getter
@Setter
@Builder
@Document(collection = "movimentacoesBancarias")
public class MovimentacoesBancarias {
    private ObjectId usuarioId;
    private ObjectId contaBancariaId;
    private TipoMovimentacoes tipo;
    private String ticker;
    private Integer quantidade;
    private BigDecimal valor;
    private LocalDate dataMovimentacao;
    private BigDecimal saldoAnterior;
    private BigDecimal saldoAtual;
}
