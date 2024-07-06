package com.alura.literalura.controller;

import com.alura.literalura.model.Libro;
import com.alura.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService libroService;

    @Autowired
    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @PostMapping
    public Libro guardarLibro(@RequestBody Libro libro) {
        return libroService.guardarLibro(libro);
    }

    @GetMapping
    public List<Libro> obtenerTodosLosLibros() {
        return libroService.obtenerTodosLosLibros();
    }

    @GetMapping("/{titulo}")
    public Libro obtenerLibroPorTitulo(@PathVariable String titulo) {
        return libroService.obtenerLibroPorTitulo(titulo);
    }

    @GetMapping("/consultar-api/{titulo}")
    public Libro consultarApiYGuardarLibro(@PathVariable String titulo) {
        return libroService.consultarApiYGuardarLibro(titulo);
    }
}
