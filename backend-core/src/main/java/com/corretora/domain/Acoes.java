package com.corretora.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document(collection = "acoes")
public class Acoes {
    private String ticker;
    private BigDecimal preco;
    private Integer quantidadeDisponivel;

    public Acoes(String ticker, BigDecimal preco, Integer quantidadeDisponivel) {
        this.ticker = ticker;
        this.preco = preco;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(Integer quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }
}
