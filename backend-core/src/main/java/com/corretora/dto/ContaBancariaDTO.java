package com.corretora.dto;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private ObjectId usuarioId;
    private BigDecimal saldoCaixa;
    private BigDecimal saldoInvestido;

    public ObjectId getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(ObjectId usuarioId) {
        this.usuarioId = usuarioId;
    }

    public BigDecimal getSaldoCaixa() {
        return saldoCaixa;
    }

    public void setSaldoCaixa(BigDecimal saldoCaixa) {
        this.saldoCaixa = saldoCaixa;
    }

    public BigDecimal getSaldoInvestido() {
        return saldoInvestido;
    }

    public void setSaldoInvestido(BigDecimal saldoInvestido) {
        this.saldoInvestido = saldoInvestido;
    }
}
