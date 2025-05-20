package com.corretora.domain;

import lombok.Builder;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Document(collection = "transacoes")
public class Transacao {
    @Id
    private String id;
    private String tipo; // COMPRA ou VENDA
    private LocalDate data;
    private BigDecimal valor;
    private String ticker;
    private Integer quantidade;
    private String usuarioId;

    public Transacao(String id, String tipo, LocalDate data, BigDecimal valor, String ticker, Integer quantidade, String usuarioId) {
        this.id = id;
        this.tipo = tipo;
        this.data = data;
        this.valor = valor;
        this.ticker = ticker;
        this.quantidade = quantidade;
        this.usuarioId = usuarioId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }
}
