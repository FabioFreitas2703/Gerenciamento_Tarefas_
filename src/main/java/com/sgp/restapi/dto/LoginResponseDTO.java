package com.sgp.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {

    private String token;
    private String tipo;
    private Long id;
    private String nome;
    private String email;
}