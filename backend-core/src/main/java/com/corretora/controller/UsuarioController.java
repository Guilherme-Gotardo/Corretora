package com.corretora.controller;

import com.corretora.domain.Usuario;
import com.corretora.dto.PerfilInvestimentoQuantidadeDTO;
import com.corretora.dto.UsuarioDTO;
import com.corretora.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Rota para inserção dos usuários
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@Valid @RequestBody UsuarioDTO dto) {
        Usuario usuarioCriado = usuarioService.criarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    // Rota para retorno de todos os usuários cadastrados
    @GetMapping("/listarUsuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    // Rota para retorno do perfil de investimento de todos os usuarios
    @GetMapping("/listarPerfis")
    public ResponseEntity<List<PerfilInvestimentoQuantidadeDTO>> quantidadePorPerfilInvestimento() {
        return ResponseEntity.ok(usuarioService.agruparPorPerfilInvestimento());
    }
}

