package com.corretora.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class PerfilInvestimentoQuantidadeDTO {
    private String perfil;
    private long quantidade;
}
