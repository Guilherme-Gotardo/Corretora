package com.corretora.dto;

import com.corretora.domain.TipoMovimentacoes;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransacaoDTO {

    @NotNull
    private String usuarioId;

    @NotBlank
    private String ticker;

    @NotNull
    private TipoMovimentacoes tipo; // COMPRA ou VENDA

    @Min(1)
    private int quantidade;

    @NotNull
    private BigDecimal precoUnitario;
}
