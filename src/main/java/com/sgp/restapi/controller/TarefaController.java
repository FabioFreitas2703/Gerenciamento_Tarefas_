package com.sgp.restapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sgp.restapi.model.Tarefa;
import com.sgp.restapi.service.TarefaService;


@RestController
@RequestMapping("/api/tarefas") 
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    // Cadastrar tarefa
    @PostMapping
    public ResponseEntity<Tarefa> criar(@RequestBody Tarefa tarefa) {
        Tarefa savedTarefa = tarefaService.salvar(tarefa);
        return ResponseEntity.status(201).body(savedTarefa);
    }

    // Listar todas as tarefas
    @GetMapping
    public List<Tarefa> listarTodos() {
        return tarefaService.listarTodos();
    }

    // Buscar tarefa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        return tarefaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar tarefa
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(
            @PathVariable Long id,
            @RequestBody Tarefa dados) {

        Tarefa atualizado = tarefaService.atualizar(id, dados);

        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(atualizado);
    }

    // Excluir tarefa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        boolean deleted = tarefaService.excluir(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}
