package com.corretora.dto;

import com.corretora.domain.TipoPerfil;
import com.corretora.domain.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO{
    @Id
    private ObjectId usuarioId;

    @NotBlank
    private String nome;

    @Email
    private String email;

    private TipoPerfil perfilInvestidor;
}
