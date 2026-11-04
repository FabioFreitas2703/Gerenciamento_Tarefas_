package com.sgp.restapi.model;

import com.sgp.restapi.enums.EnumStatusTarefa;
import com.sgp.restapi.enums.EnumPrioridade;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Column;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import com.fasterxml.jackson.annotation.JsonFormat;


@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Entity 
@Table (name = "tarefa")
public class Tarefa {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column (nullable = false, length = 100)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @JsonFormat (pattern = "dd/MM/yyyy HH:mm:ss")
    @Column (name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @JsonFormat (pattern = "dd/MM/yyyy")
    @Column (name = "data_prazo")
    private LocalDate dataPrazo;

    @JsonFormat (pattern = "dd/MM/yyyy")
    @Column (name = "data_conclusao")   
    private LocalDate dataConclusao;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false,length = 20)
    private EnumStatusTarefa status = EnumStatusTarefa.PENDENTE;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false,length = 20)  
    private EnumPrioridade prioridade = EnumPrioridade.MEDIA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projeto_id", nullable = false)
    @ToString.Exclude
    private Projeto projeto;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_responsavel_id")
    @ToString.Exclude
    private Usuario usuarioResponsavel;

    @PrePersist 
    public void prePersist() {
        if (dataCriacao == null) {
            dataCriacao = LocalDateTime.now();
        }
    }


}
