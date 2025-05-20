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
    @NotNull
    private ObjectId usuarioId;

    private BigDecimal saldoCaixa;
    private BigDecimal saldoInvestido;

    @NotNull
    @PositiveOrZero
    private BigDecimal saldoInicial;
}
