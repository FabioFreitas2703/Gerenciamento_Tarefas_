package com.sgp.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgp.restapi.model.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    
}