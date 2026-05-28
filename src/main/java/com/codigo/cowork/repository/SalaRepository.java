package com.codigo.cowork.repository;

import com.codigo.cowork.model.Sala;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class SalaRepository {

    private final List<Sala> salas = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong(1);

    public List<Sala> findAll() {
        return new ArrayList<>(salas);
    }

    public Optional<Sala> findById(Long id) {
        return salas.stream()
                .filter(sala -> sala.getId().equals(id))
                .findFirst();
    }

    public Sala save(Sala sala) {
        sala.setId(contador.getAndIncrement());
        salas.add(sala);
        return sala;
    }

    public Sala update(Sala sala) {
        return sala;
    }

    public void deleteById(Long id) {
        salas.removeIf(sala -> sala.getId().equals(id));
    }
}
