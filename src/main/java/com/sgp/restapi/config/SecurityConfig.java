package com.sgp.restapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sgp.restapi.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .headers(headers ->
                headers.frameOptions(frame -> frame.sameOrigin())
            )

            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable())

            .authorizeHttpRequests(auth -> auth

                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**"
                ).permitAll()

                .requestMatchers("/h2-console/**").permitAll()

                .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()

                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

                .anyRequest().authenticated()
            )

            // Tratamento de erros de autenticação e autorização
            .exceptionHandling(exception -> exception

                .authenticationEntryPoint((request, response, ex) -> {
                    response.setStatus(401);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    response.getWriter().write(
                        "{\"error\":\"Autenticação necessária. Informe um token JWT válido.\"}"
                    );
                })

                .accessDeniedHandler((request, response, ex) -> {
                    response.setStatus(403);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    response.getWriter().write(
                        "{\"error\":\"Você não tem permissão para acessar este recurso.\"}"
                    );
                })
            )

            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}