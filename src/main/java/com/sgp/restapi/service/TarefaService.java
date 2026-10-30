package com.sgp.restapi.service;

import java.util.List;
import java.util.Optional;

import com.sgp.restapi.model.Projeto;
import com.sgp.restapi.model.Usuario;
import com.sgp.restapi.model.Tarefa;

import com.sgp.restapi.repository.ProjetoRepository;  
import com.sgp.restapi.repository.UsuarioRepository;
import com.sgp.restapi.repository.TarefaRepository;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;


  

@Service 
public class TarefaService {
    
    private final TarefaRepository tarefaRepository;
    private final ProjetoRepository projetoRepository;
    private final UsuarioRepository usuarioRepository;

    public TarefaService(TarefaRepository tarefaRepository,
            ProjetoRepository projetoRepository,
            UsuarioRepository usuarioRepository) {
        this.tarefaRepository = tarefaRepository;
        this.projetoRepository = projetoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Tarefa salvar(Tarefa tarefa) {
        tarefa.setId(null);

        if (tarefa.getProjeto() == null || tarefa.getProjeto().getId() == null) {
            throw new IllegalArgumentException("O Projeto é obrigatorio");
        }

        Projeto projeto = projetoRepository.findById(tarefa.getProjeto().getId())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado " ));
        tarefa.setProjeto(projeto);

        if (tarefa.getUsuarioResponsavel() != null) {
            Usuario usuario = usuarioRepository.findById(tarefa.getUsuarioResponsavel().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado " ));
            tarefa.setUsuarioResponsavel(usuario);
        }
        return tarefaRepository.save(tarefa);
    }

    public List<Tarefa> listarTodos() {
        return tarefaRepository.findAll();
    }

    public Optional<Tarefa> buscarPorId(Long id) {
        return tarefaRepository.findById(id);
    }

  public Tarefa atualizar(Long id, Tarefa dados) {

    return tarefaRepository.findById(id)
            .map(tarefa -> {

                if (dados.getProjeto() == null || dados.getProjeto().getId() == null) {
                    throw new IllegalArgumentException("O projeto é obrigatório");
                }

                Projeto projeto = projetoRepository.findById(dados.getProjeto().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));

                tarefa.setProjeto(projeto);

                if (dados.getUsuarioResponsavel() != null) {

                    Usuario usuario = usuarioRepository.findById(
                            dados.getUsuarioResponsavel().getId())
                            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

                    tarefa.setUsuarioResponsavel(usuario);

                } else {
                    tarefa.setUsuarioResponsavel(null);
                }

                tarefa.setTitulo(dados.getTitulo());
                tarefa.setDescricao(dados.getDescricao());
                tarefa.setDataPrazo(dados.getDataPrazo());
                tarefa.setDataConclusao(dados.getDataConclusao());
                tarefa.setStatus(dados.getStatus());
                tarefa.setPrioridade(dados.getPrioridade());

                return tarefaRepository.save(tarefa);
            })
            .orElseThrow(() -> new EntityNotFoundException(
                    "Tarefa não encontrada com o ID: " + id));
    }
    
    
    public boolean excluir(Long id) {
        return tarefaRepository.findById(id)
                .map(tarefa -> {
                    tarefaRepository.delete(tarefa);
                    return true;
                })
                .orElse(false);
    }
  
}
