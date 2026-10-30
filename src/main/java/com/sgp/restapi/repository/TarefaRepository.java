package com.sgp.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sgp.restapi.model.Tarefa;


public interface TarefaRepository extends JpaRepository<Tarefa, Long> { 

    
}
