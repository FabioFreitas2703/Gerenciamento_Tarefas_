package com.sgp.restapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sgp.restapi.model.Projeto;
import com.sgp.restapi.service.ProjetoService;

@RestController
@RequestMapping("/api/projetos")

public class ProjetoController {

    private final ProjetoService projetoService;

    public ProjetoController(ProjetoService projetoService) {
        this.projetoService = projetoService;
    }

    // Cadastrar projeto
    @PostMapping
    public Projeto criar(@RequestBody Projeto projeto) {
        return projetoService.salvar(projeto);
    }

    // Listar todos os projetos
    @GetMapping
    public List<Projeto> listarTodos() {
        return projetoService.listarTodos();
    }

    // Buscar projeto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Projeto> buscarPorId(@PathVariable Long id) {
        return projetoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar projeto
    @PutMapping("/{id}")
    public ResponseEntity<Projeto> atualizar(
            @PathVariable Long id,
            @RequestBody Projeto dados) {

        Projeto atualizado = projetoService.atualizar(id, dados);

        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(atualizado);
    }

    // Excluir projeto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        boolean excluido = projetoService.excluir(id);

        if (!excluido) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
    
}
