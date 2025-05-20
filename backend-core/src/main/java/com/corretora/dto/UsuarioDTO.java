package com.corretora.dto;

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

    // Vou criar a DTO tirando a necessidade do frontend ter que cadastrar uma lista de produtos para o usuário
    @Id
    private ObjectId usuarioId;

    @NotBlank
    private String nome;

    @Email
    private String email;

    @NotBlank
    private String perfil;

}
