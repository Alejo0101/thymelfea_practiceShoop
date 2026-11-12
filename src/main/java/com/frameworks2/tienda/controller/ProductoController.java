package com.frameworks2.tienda.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.frameworks2.tienda.entity.Producto;
import com.frameworks2.tienda.repo.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoRepository repo;

    @ModelAttribute("categorias")
    public List<String> categorias() {
        return List.of("Tecnología", "Hogar", "Ropa", "Deportes");
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String nombre, Model model) {
        List<Producto> productos = (nombre == null || nombre.isBlank())
                ? repo.findAll()
                : repo.findByNombreContainingIgnoreCase(nombre);
        model.addAttribute("productos", productos);
        model.addAttribute("nombre", nombre);
        return "productos/lista";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("producto", buscar(id));
        return "productos/detalle";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("producto", new Producto());
        return "productos/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("producto", buscar(id));
        return "productos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto, RedirectAttributes ra) {
        repo.save(producto);
        ra.addFlashAttribute("mensaje", "Producto guardado correctamente");
        return "redirect:/productos";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        repo.deleteById(id);
        ra.addFlashAttribute("mensaje", "Producto eliminado");
        return "redirect:/productos";
    }

    private Producto buscar(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }
}
