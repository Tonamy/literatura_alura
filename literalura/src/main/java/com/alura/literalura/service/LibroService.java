package com.alura.literalura.service;

import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.LibroRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public LibroService(LibroRepository libroRepository, RestTemplate restTemplate) {
        this.libroRepository = libroRepository;
        this.restTemplate = restTemplate;
    }

    public Libro guardarLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    public List<Libro> obtenerTodosLosLibros() {
        return libroRepository.findAll();
    }

    public Libro obtenerLibroPorTitulo(String titulo) {
        return libroRepository.findByTitulo(titulo);
    }

    public Libro consultarApiYGuardarLibro(String titulo) {
        String url = "https://gutendex.com/books?search=" + titulo;
        String respuesta = restTemplate.getForObject(url, String.class);

        Libro libro = parsearRespuestaJson(respuesta);

        return guardarLibro(libro);
    }

    private Libro parsearRespuestaJson(String respuesta) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(respuesta);
            JsonNode results = root.path("results").get(0); // Tomar el primer resultado
            String titulo = results.path("title").asText();
            String autor = results.path("authors").get(0).path("name").asText(); // Tomar el primer autor
            String idioma = results.path("languages").get(0).asText(); // Tomar el primer idioma
            int numeroDescargas = results.path("download_count").asInt();

            return new Libro(titulo, autor, idioma, numeroDescargas);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
