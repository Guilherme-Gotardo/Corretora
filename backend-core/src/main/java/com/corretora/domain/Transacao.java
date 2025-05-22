package com.corretora.domain;

import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "transacoes")
public class Transacao {
    @Id
    private String id;
    private ObjectId usuarioId;
    private TipoMovimentacoes tipo;
    private String ticker;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private LocalDateTime dataTransacao;
}

