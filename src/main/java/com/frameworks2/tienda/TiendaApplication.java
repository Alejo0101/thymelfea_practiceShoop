package com.frameworks2.tienda;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.frameworks2.tienda.entity.Producto;
import com.frameworks2.tienda.entity.Rol;
import com.frameworks2.tienda.entity.Usuario;
import com.frameworks2.tienda.repo.ProductoRepository;
import com.frameworks2.tienda.repo.UsuarioRepository;

@SpringBootApplication
public class TiendaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TiendaApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatos(UsuarioRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.count() == 0) {
                crear(repo, encoder, "admin", "admin123", Rol.ADMIN);
                crear(repo, encoder, "vendedor", "vend123", Rol.VENDEDOR);
                crear(repo, encoder, "cliente", "cli123", Rol.CLIENTE);
            }
        };
    }

    @Bean
    CommandLineRunner initProductos(ProductoRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(nuevo("Portátil HP", "Intel i5, 16 GB de RAM", "2800000", 8, "Tecnología"));
                repo.save(nuevo("Mouse inalámbrico", "Sensor óptico, batería recargable", "65000", 40, "Tecnología"));
                repo.save(nuevo("Licuadora 600W", "Vaso de vidrio de 1,5 L", "180000", 3, "Hogar"));
                repo.save(nuevo("Camiseta deportiva", "Tela transpirable", "55000", 25, "Ropa"));
                repo.save(nuevo("Balón de fútbol", "Tamaño oficial número 5", "90000", 0, "Deportes"));
            }
        };
    }

    private Producto nuevo(String nombre, String descripcion, String precio, int stock, String categoria) {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(new BigDecimal(precio));
        producto.setStock(stock);
        producto.setCategoria(categoria);
        return producto;
    }

    private void crear(UsuarioRepository repo, PasswordEncoder encoder,
            String username, String clave, Rol rol) {
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(encoder.encode(clave));
        usuario.setRol(rol);
        repo.save(usuario);
    }
}
