package com.sgp.restapi.model;

import java.time.LocalDate;
import com.sgp.restapi.enums.EnumStatusUsuario;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; 
import lombok.ToString;

import java.util.List;


@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column (name = "data_nascimento")
    private LocalDate dataNascimento;


    @Enumerated(EnumType.STRING)
    @Column (nullable = false,length = 20)
    private EnumStatusUsuario status = EnumStatusUsuario.ATIVO;

    @OneToMany(mappedBy = "usuarioResponsavel")
    @ToString.Exclude
    private List<Tarefa> tarefasResponsavel;
    
}
