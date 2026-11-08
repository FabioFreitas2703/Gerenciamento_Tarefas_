package com.sgp.restapi.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sgp.restapi.model.Usuario;
import com.sgp.restapi.repository.UsuarioRepository;
import com.sgp.restapi.enums.EnumStatusUsuario;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        String email = "admin@sgp.com";

        if (!usuarioRepository.existsByEmail(email)) {

            Usuario admin = new Usuario();

            admin.setNome("Administrador");
            admin.setEmail(email);
            admin.setSenha(passwordEncoder.encode("Admin@123"));
            admin.setStatus(EnumStatusUsuario.ATIVO);

            usuarioRepository.save(admin);

            System.out.println("Administrador padrão criado com sucesso!");
        }
    }
}