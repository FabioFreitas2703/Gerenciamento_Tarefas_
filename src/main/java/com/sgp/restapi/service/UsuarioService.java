package com.sgp.restapi.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.sgp.restapi.model.Usuario;
import com.sgp.restapi.repository.UsuarioRepository; 
import org.springframework.security.crypto.password.PasswordEncoder;  

@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, 
        PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public Usuario salvar(Usuario usuario) {
        usuario.setId(null);
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new EmailJaCadastradoException("Email já cadastrado");
        }

        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            throw new IllegalArgumentException("Senha não pode ser nula ou vazia");
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario atualizar(Long id, Usuario dados) {
        return usuarioRepository.findById(id)
                .map(usuario -> {

                    if(!usuario.getEmail().equals(dados.getEmail()) 
                        && usuarioRepository.existsByEmail(dados.getEmail())) {
                        throw new EmailJaCadastradoException("Email já cadastrado");
                    }
                    
                    usuario.setNome(dados.getNome());
                    usuario.setEmail(dados.getEmail());

                
                    
                    if(dados.getSenha() != null && !dados.getSenha().isBlank()) {
                        usuario.setSenha(passwordEncoder.encode(dados.getSenha()));
                    }
 
                    
                    usuario.setDataNascimento(dados.getDataNascimento());
                
                    return usuarioRepository.save(usuario);
                })
                .orElse(null);
    }

    public boolean excluir(Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuarioRepository.delete(usuario);
                    return true;
                })
                .orElse(false);
    }
}
