package com.frameworks2.tienda.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frameworks2.tienda.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}
