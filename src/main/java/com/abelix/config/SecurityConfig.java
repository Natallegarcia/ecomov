package com.abelix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.abelix.repository.UsuarioRepository;
import com.abelix.model.UsuarioModel;
import java.util.Optional;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
	
	private final UsuarioRepository usuarioRepository;

    public SecurityConfig(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/usuario/salvar", "/usuario/login").permitAll() // Rotas públicas
                .anyRequest().authenticated()  //Solicita a  autenticação em todas as outras rotas
            )
            .httpBasic(httpBasic -> httpBasic
                .realmName("YourAppRealm")
            )
            .userDetailsService(userDetailsService());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<UsuarioModel> usuario = usuarioRepository.findByLogin(username);
            if (usuario.isEmpty()) {
                throw new UsernameNotFoundException("Usuário não encontrado: " + username);
            }
            return org.springframework.security.core.userdetails.User
                .withUsername(usuario.get().getLogin())
                .password(usuario.get().getPassword())
                .roles("USER")
                .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
