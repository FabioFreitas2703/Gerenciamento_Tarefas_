package com.sgp.restapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.sgp.restapi.dto.LoginRequestDTO;
import com.sgp.restapi.dto.LoginResponseDTO;
import com.sgp.restapi.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO dados) {

        return ResponseEntity.ok(authService.login(dados));
    }
}