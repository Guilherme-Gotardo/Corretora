package com.corretora.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;

import java.math.BigDecimal;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContaBancariaDTO {
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
