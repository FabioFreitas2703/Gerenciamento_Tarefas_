package com.sgp.restapi.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.sgp.restapi.service.JwtService;
import com.sgp.restapi.service.UsuarioService;
import com.sgp.restapi.model.Usuario;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UsuarioService usuarioService) {

        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(7);

        if (!jwtService.validarToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String email = jwtService.extrairEmail(token);

        if (SecurityContextHolder.getContext().getAuthentication() == null) {

            Usuario usuario = usuarioService.buscarPorEmail(email)
                    .orElse(null);

            if (usuario != null && usuario.getStatus().name().equals("ATIVO")) {

                UserDetails userDetails = User
                        .withUsername(usuario.getEmail())
                        .password(usuario.getSenha())
                        .authorities("ROLE_USER")
                        .build();

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}