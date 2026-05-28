package com.codigo.cowork.repository;

import com.codigo.cowork.model.Sala;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class SalaRepository {

    private final Map<Long, Sala> almacen = new LinkedHashMap<>();
    private final AtomicLong secuencia = new AtomicLong(1);

    public List<Sala> findAll() {
        return List.copyOf(almacen.values());
    }

    public Optional<Sala> findById(Long id) {
        return Optional.ofNullable(almacen.get(id));
    }

    public Sala save(Sala sala) {
        long nuevoId = secuencia.getAndIncrement();
        sala.setId(nuevoId);
        almacen.put(nuevoId, sala);
        return sala;
    }

    public Sala update(Sala sala) {
        almacen.put(sala.getId(), sala);
        return sala;
    }

    public void deleteById(Long id) {
        almacen.remove(id);
    }

    public boolean existsById(Long id) {
        return almacen.containsKey(id);
    }
}
