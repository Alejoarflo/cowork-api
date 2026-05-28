package com.codigo.cowork.service;

import com.codigo.cowork.dto.ComprobanteResponseDTO;
import com.codigo.cowork.dto.ReservaRequestDTO;
import com.codigo.cowork.dto.ReservaResponseDTO;
import com.codigo.cowork.mapper.ReservaMapper;
import com.codigo.cowork.model.Reserva;
import com.codigo.cowork.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservaService {

    private static final String ESTADO_INICIAL = "PENDIENTE";
    private static final String CLAVE_INTERNA  = "COWORK-SEC-2026";

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public ReservaResponseDTO crear(ReservaRequestDTO dto) {
        validarHorario(dto);
        Reserva reserva = ReservaMapper.toModel(dto);
        reserva.setEstado(ESTADO_INICIAL);
        reserva.setPasswordInterno(CLAVE_INTERNA);
        return ReservaMapper.toResponseDTO(reservaRepository.save(reserva));
    }

    public ReservaResponseDTO obtenerPorId(Long id) {
        return ReservaMapper.toResponseDTO(resolverReserva(id));
    }

    public List<ReservaResponseDTO> listar(String estado, LocalDate fecha, Long salaId) {
        return reservaRepository.findAll().stream()
                .filter(r -> estado == null  || estado.equalsIgnoreCase(r.getEstado()))
                .filter(r -> fecha  == null  || fecha.equals(r.getFecha()))
                .filter(r -> salaId == null  || salaId.equals(r.getSalaId()))
                .map(ReservaMapper::toResponseDTO)
                .toList();
    }

    public List<ReservaResponseDTO> listarPorSala(Long salaId) {
        return listar(null, null, salaId);
    }

    public ReservaResponseDTO cambiarEstado(Long id, String nuevoEstado) {
        Reserva reserva = resolverReserva(id);
        reserva.setEstado(validarEstado(nuevoEstado));
        return ReservaMapper.toResponseDTO(reservaRepository.update(reserva));
    }

    public void eliminar(Long id) {
        resolverReserva(id);
        reservaRepository.deleteById(id);
    }

    public ComprobanteResponseDTO registrarComprobante(Long id, MultipartFile archivo, String clienteId) {
        resolverReserva(id);
        if (archivo == null || archivo.isEmpty()) {
            throw new RuntimeException("El archivo PDF no puede estar vacio");
        }
        String mensaje = "Comprobante recibido para reserva #" + id;
        return new ComprobanteResponseDTO(id, clienteId, archivo.getOriginalFilename(), archivo.getSize(), mensaje);
    }

    // ---------- helpers privados ----------

    private Reserva resolverReserva(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));
    }

    private String validarEstado(String estado) {
        if (estado == null || estado.isBlank()) {
            throw new RuntimeException("El estado es obligatorio");
        }
        String normalizado = estado.trim().toUpperCase();
        return switch (normalizado) {
            case "PENDIENTE", "CONFIRMADA", "CANCELADA" -> normalizado;
            default -> throw new RuntimeException(
                    "Estado '" + normalizado + "' no valido. Use: PENDIENTE, CONFIRMADA o CANCELADA");
        };
    }

    private void validarHorario(ReservaRequestDTO dto) {
        if (dto.horaFin() != null && dto.horaInicio() != null
                && !dto.horaFin().isAfter(dto.horaInicio())) {
            throw new RuntimeException("La hora de fin debe ser posterior a la hora de inicio");
        }
    }
}
