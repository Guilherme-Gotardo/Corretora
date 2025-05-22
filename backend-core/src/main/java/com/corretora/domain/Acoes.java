package com.corretora.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@Setter
@Getter
@Document(collection = "acoes")
public class Acoes {
    private String ticker;
    private BigDecimal preco;
    private Integer quantidadeDisponivel;
}

