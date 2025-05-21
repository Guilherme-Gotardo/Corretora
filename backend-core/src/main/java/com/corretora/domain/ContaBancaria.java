package com.corretora.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Setter
@Getter
@Data
@Builder
@Document(collection = "contasBancarias")
public class ContaBancaria {
    @Id
    ObjectId contaId;

    @NotNull
    private ObjectId usuarioId;

    @NotBlank
    private String banco;

    @NotBlank
    private String numeroConta;

    @NotBlank
    private String agencia;

    @Builder.Default
    private BigDecimal saldoCaixa = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal saldoInvestido = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal saldoInicial = BigDecimal.ZERO;
}

