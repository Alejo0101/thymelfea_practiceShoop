package com.frameworks2.tienda.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.frameworks2.tienda.entity.Usuario;
import com.frameworks2.tienda.repo.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {

        private final UsuarioRepository repo;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Usuario u = repo.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("No existe: " +
                                                username));

                return User.withUsername(u.getUsername())
                                .password(u.getPassword())
                                .roles(u.getRol().name())
                                .build();
        }
}