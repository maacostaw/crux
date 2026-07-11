package com.example.javaservice.service;

import com.example.javaservice.model.Estudiante;
import com.example.javaservice.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {

    private final EstudianteRepository repository;

    public EstudianteService(EstudianteRepository repository) {
        this.repository = repository;
    }

    public List<Estudiante> findAll() {
        return repository.findAll();
    }

    public Optional<Estudiante> findById(Long id) {
        return repository.findById(id);
    }

    public Estudiante create(Estudiante estudiante) {
        estudiante.setId(null);
        return repository.save(estudiante);
    }

    public Optional<Estudiante> update(Long id, Estudiante datos) {
        return repository.findById(id).map(existente -> {
            existente.setNombre(datos.getNombre());
            return repository.save(existente);
        });
    }

    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
