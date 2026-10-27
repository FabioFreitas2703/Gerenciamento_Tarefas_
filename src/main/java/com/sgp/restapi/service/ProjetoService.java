package com.sgp.restapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sgp.restapi.model.Projeto;
import com.sgp.restapi.repository.ProjetoRepository;

@Service 
public class ProjetoService {
     private final ProjetoRepository projetoRepository;

    public ProjetoService(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    public Projeto salvar(Projeto projeto) {
        projeto.setId(null);
        return projetoRepository.save(projeto);
    }

    public List<Projeto> listarTodos() {
        return projetoRepository.findAll();
    }

    public Optional<Projeto> buscarPorId(Long id) {
        return projetoRepository.findById(id);
    }

    public Projeto atualizar(Long id, Projeto dados) {
        return projetoRepository.findById(id)
                .map(projeto -> {
                    projeto.setNome(dados.getNome());
                    projeto.setInicio(dados.getInicio());
                    projeto.setConclusao(dados.getConclusao());
                    projeto.setStatus(dados.getStatus());
                    return projetoRepository.save(projeto);
                    
                })
                .orElse(null);
    }
// receba esta bencao varao
    public boolean excluir(Long id) {
        return projetoRepository.findById(id)
                .map(projeto -> {
                    projetoRepository.delete(projeto);
                    return true;
                })
                .orElse(false);
    }
}
