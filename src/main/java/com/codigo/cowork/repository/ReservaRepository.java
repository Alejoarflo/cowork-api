package com.codigo.cowork.repository;

import com.codigo.cowork.model.Reserva;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class ReservaRepository {

    private final Map<Long, Reserva> almacen = new LinkedHashMap<>();
    private final AtomicLong secuencia = new AtomicLong(1);

    public List<Reserva> findAll() {
        return List.copyOf(almacen.values());
    }

    public Optional<Reserva> findById(Long id) {
        return Optional.ofNullable(almacen.get(id));
    }

    public Reserva save(Reserva reserva) {
        long nuevoId = secuencia.getAndIncrement();
        reserva.setId(nuevoId);
        almacen.put(nuevoId, reserva);
        return reserva;
    }

    public Reserva update(Reserva reserva) {
        almacen.put(reserva.getId(), reserva);
        return reserva;
    }

    public void deleteById(Long id) {
        almacen.remove(id);
    }

    public List<Reserva> findBySalaId(Long salaId) {
        return almacen.values().stream()
                .filter(r -> r.getSalaId().equals(salaId))
                .collect(Collectors.toList());
    }

    public void deleteBySalaId(Long salaId) {
        almacen.values().removeIf(r -> r.getSalaId().equals(salaId));
    }
}
