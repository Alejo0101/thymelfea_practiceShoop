package com.frameworks2.tienda.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PaginaController {
    @GetMapping("/{slug}")
    public String inicio(@PathVariable("slug") String slug,
            @RequestParam(name = "categoria", required = false) String categoria, Model datos) {
        datos.addAttribute("mensaje", "Bienvenido al detalle de " + slug + ", " + categoria);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/cliente")
    public String cliente() {
        return "cliente";
    }

    @GetMapping("/vendedor")
    public String vendedor() {
        return "vendedor";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "acceso-denegado";
    }

    @GetMapping("/redirigir")
    public String redirigir(Authentication auth) {
        var roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        if (roles.contains("ROLE_ADMIN"))
            return "redirect:/admin";
        if (roles.contains("ROLE_VENDEDOR"))
            return "redirect:/vendedor";
        return "redirect:/cliente";
    }
}
