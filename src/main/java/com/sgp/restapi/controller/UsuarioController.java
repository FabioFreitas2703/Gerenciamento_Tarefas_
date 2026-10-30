package com.sgp.restapi.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sgp.restapi.model.Usuario;
import com.sgp.restapi.service.UsuarioService;

import jakarta.validation.Valid;

import com.sgp.restapi.dto.UsuarioRequestDTO;
import com.sgp.restapi.dto.UsuarioResponseDTO;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    private UsuarioResponseDTO convertToDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getDataNascimento(),
            usuario.getStatus()
        );
    }
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    private Usuario convertParaUsuario(UsuarioRequestDTO dados) {
        Usuario usuario = new Usuario();
        usuario.setNome(dados.getNome());
        usuario.setEmail(dados.getEmail());
        usuario.setSenha(dados.getSenha());
        usuario.setDataNascimento(dados.getDataNascimento());
        return usuario;
    }

    // Cadastrar usuário
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criar(@Valid @RequestBody UsuarioRequestDTO dados) {
        Usuario usuario = convertParaUsuario(dados);
        Usuario savedUsuario = usuarioService.salvar(usuario);
        UsuarioResponseDTO usuarioDTO = convertToDTO(savedUsuario);
        return ResponseEntity.status(201).body(usuarioDTO);
    }

    // Listar todos os usuários
    @GetMapping
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioService.listarTodos()
        .stream()
        .map(this::convertToDTO)
        .toList();
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar usuário
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dados) {
            
        Usuario usuario = convertParaUsuario(dados);

        Usuario atualizado = usuarioService.atualizar(id, usuario);

        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(convertToDTO(atualizado));
    }

    // Excluir usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        boolean excluido  = usuarioService.excluir(id);
        if (excluido) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
