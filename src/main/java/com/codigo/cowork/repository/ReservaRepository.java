package com.codigo.cowork.repository;

import com.codigo.cowork.model.Reserva;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservaRepository {

    private final List<Reserva> reservas = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong(1);

    public List<Reserva> findAll() {
        return new ArrayList<>(reservas);
    }

    public Optional<Reserva> findById(Long id) {
        return reservas.stream()
                .filter(reserva -> reserva.getId().equals(id))
                .findFirst();
    }

    public Reserva save(Reserva reserva) {
        reserva.setId(contador.getAndIncrement());
        reservas.add(reserva);
        return reserva;
    }

    public Reserva update(Reserva reserva) {
        return reserva;
    }

    public void deleteById(Long id) {
        reservas.removeIf(reserva -> reserva.getId().equals(id));
    }

    public List<Reserva> findBySalaId(Long salaId) {
        return reservas.stream()
                .filter(r -> r.getSalaId().equals(salaId))
                .collect(java.util.stream.Collectors.toList());
    }

    public void deleteBySalaId(Long salaId) {
        reservas.removeIf(reserva -> reserva.getSalaId().equals(salaId));
    }
}
