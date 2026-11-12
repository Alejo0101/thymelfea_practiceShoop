package com.frameworks2.tienda.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frameworks2.tienda.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
}
