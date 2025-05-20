package com.corretora.domain;

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
    private ObjectId contaId;

    private ObjectId usuarioId;
    private BigDecimal saldoCaixa;
    private BigDecimal saldoInvestido;
}
