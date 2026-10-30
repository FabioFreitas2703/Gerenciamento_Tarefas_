package com.sgp.restapi.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import com.sgp.restapi.enums.EnumStatusProjeto;
import jakarta.persistence.Column;

@Entity 
@Table(name = "projeto")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 

public class Projeto {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nome;

    @Column (name = "data_inicio", nullable = false)
    private LocalDate inicio;

    @Column (name = "data_conclusao")
    private LocalDate conclusao;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false,length = 20)
    private EnumStatusProjeto status = EnumStatusProjeto.ATIVO;
}
