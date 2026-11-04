package com.sgp.restapi.service;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sgp.restapi.dto.LoginRequestDTO;
import com.sgp.restapi.dto.LoginResponseDTO;
import com.sgp.restapi.model.Usuario;

import com.sgp.restapi.enums.EnumStatusUsuario;

@Service
public class AuthService {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UsuarioService usuarioService,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponseDTO login(LoginRequestDTO dados) {

        Usuario usuario = usuarioService.buscarPorEmail(dados.getEmail())
                .orElseThrow(() ->
                        new IllegalArgumentException("Email ou senha inválidos"));

        if (!passwordEncoder.matches(dados.getSenha(), usuario.getSenha())) {
            throw new IllegalArgumentException("Email ou senha inválidos");
        }

        if (usuario.getStatus() != EnumStatusUsuario.ATIVO) {
            throw new IllegalArgumentException("Usuário inativo ou bloqueado");
        }

        String token = jwtService.gerarToken(usuario.getEmail());

        return new LoginResponseDTO(
                token,
                "Bearer",
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }
}