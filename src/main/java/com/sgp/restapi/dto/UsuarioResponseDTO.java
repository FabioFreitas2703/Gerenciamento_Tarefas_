package com.sgp.restapi.dto;

import java.time.LocalDate;
import com.sgp.restapi.enums.EnumStatusUsuario; 

import com.fasterxml.jackson.annotation.JsonFormat;


public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;
    private EnumStatusUsuario status;

    public UsuarioResponseDTO(Long id, String nome, String email, LocalDate dataNascimento, EnumStatusUsuario status) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public EnumStatusUsuario getStatus() {
        return status;
    }

    public void setStatus(EnumStatusUsuario status) {
        this.status = status;
    }
}
