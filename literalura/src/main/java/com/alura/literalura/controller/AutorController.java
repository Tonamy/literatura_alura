package com.alura.literalura.controller;

import com.alura.literalura.model.Autor;
import com.alura.literalura.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

    private final AutorService autorService;

    @Autowired
    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping
    public Autor guardarAutor(@RequestBody Autor autor) {
        return autorService.guardarAutor(autor);
    }

    @GetMapping
    public List<Autor> obtenerTodosLosAutores() {
        return autorService.obtenerTodosLosAutores();
    }

    @GetMapping("/{nombre}")
    public Autor obtenerAutorPorNombre(@PathVariable String nombre) {
        return autorService.obtenerAutorPorNombre(nombre);
    }
}
