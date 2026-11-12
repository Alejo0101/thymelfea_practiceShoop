package com.frameworks2.tienda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/acceso-denegado", "/css/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/vendedor/**").hasAnyRole("VENDEDOR", "ADMIN")
                        .requestMatchers("/cliente/**").hasAnyRole("CLIENTE", "ADMIN")
                        .requestMatchers("/productos/nuevo", "/productos/guardar", "/productos/*/editar", "/productos/*/eliminar")
                        .hasAnyRole("VENDEDOR", "ADMIN")
                        .requestMatchers("/productos/**")
                        .hasAnyRole("CLIENTE", "VENDEDOR", "ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/redirigir", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/"))
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/acceso-denegado"));
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
