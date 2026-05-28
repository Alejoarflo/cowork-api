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
import java.util.Set;

@Service
public class ReservaService {

    private static final String ESTADO_PENDIENTE = "PENDIENTE";
    private static final String PASSWORD_INTERNO = "COWORK-INTERNO";
    private static final Set<String> ESTADOS_VALIDOS = Set.of("PENDIENTE", "CONFIRMADA", "CANCELADA");

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public ReservaResponseDTO crear(ReservaRequestDTO requestDTO) {
        Reserva reserva = ReservaMapper.toModel(requestDTO);
        reserva.setEstado(ESTADO_PENDIENTE);
        reserva.setPasswordInterno(PASSWORD_INTERNO);
        Reserva reservaGuardada = reservaRepository.save(reserva);
        return ReservaMapper.toResponseDTO(reservaGuardada);
    }

    public ReservaResponseDTO obtenerPorId(Long id) {
        Reserva reserva = buscarReserva(id);
        return ReservaMapper.toResponseDTO(reserva);
    }

    public List<ReservaResponseDTO> listar(String estado, LocalDate fecha, Long salaId) {
        return reservaRepository.findAll().stream()
                .filter(reserva -> estado == null || reserva.getEstado().equalsIgnoreCase(estado))
                .filter(reserva -> fecha == null || reserva.getFecha().equals(fecha))
                .filter(reserva -> salaId == null || reserva.getSalaId().equals(salaId))
                .map(ReservaMapper::toResponseDTO)
                .toList();
    }

    public List<ReservaResponseDTO> listarPorSala(Long salaId) {
        return listar(null, null, salaId);
    }

    public ReservaResponseDTO cambiarEstado(Long id, String nuevoEstado) {
        String estadoNormalizado = normalizarEstado(nuevoEstado);
        Reserva reserva = buscarReserva(id);
        reserva.setEstado(estadoNormalizado);
        Reserva reservaActualizada = reservaRepository.update(reserva);
        return ReservaMapper.toResponseDTO(reservaActualizada);
    }

    public void eliminar(Long id) {
        buscarReserva(id);
        reservaRepository.deleteById(id);
    }

    public ComprobanteResponseDTO registrarComprobante(Long id, MultipartFile archivo, String clienteId) {
        buscarReserva(id);
        if (archivo == null || archivo.isEmpty()) {
            throw new RuntimeException("Debe enviar un archivo PDF no vacio");
        }
        return new ComprobanteResponseDTO(id, clienteId, archivo.getOriginalFilename(), archivo.getSize());
    }

    private Reserva buscarReserva(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe una reserva con id " + id));
    }

    private String normalizarEstado(String nuevoEstado) {
        if (nuevoEstado == null || nuevoEstado.isBlank()) {
            throw new RuntimeException("El nuevo estado es obligatorio");
        }
        String estadoNormalizado = nuevoEstado.trim().toUpperCase();
        if (!ESTADOS_VALIDOS.contains(estadoNormalizado)) {
            throw new RuntimeException("Estado invalido. Use PENDIENTE, CONFIRMADA o CANCELADA");
        }
        return estadoNormalizado;
    }
}
