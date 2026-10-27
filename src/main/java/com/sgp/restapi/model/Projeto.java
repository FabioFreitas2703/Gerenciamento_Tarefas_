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

@Entity 
@Table(name = "projeto")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 

public class Projeto {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private LocalDate inicio;

    private LocalDate conclusao;

    @Enumerated(EnumType.STRING)
    private EnumStatusProjeto status;
}
