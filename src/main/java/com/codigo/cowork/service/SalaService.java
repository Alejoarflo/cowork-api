package com.codigo.cowork.service;

import com.codigo.cowork.dto.SalaRequestDTO;
import com.codigo.cowork.dto.SalaResponseDTO;
import com.codigo.cowork.mapper.SalaMapper;
import com.codigo.cowork.model.Sala;
import com.codigo.cowork.repository.ReservaRepository;
import com.codigo.cowork.repository.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {

    private final SalaRepository salaRepository;
    private final ReservaRepository reservaRepository;

    public SalaService(SalaRepository salaRepository, ReservaRepository reservaRepository) {
        this.salaRepository = salaRepository;
        this.reservaRepository = reservaRepository;
    }

    public List<SalaResponseDTO> listar() {
        return salaRepository.findAll().stream()
                .map(SalaMapper::toResponseDTO)
                .toList();
    }

    public SalaResponseDTO obtenerPorId(Long id) {
        return SalaMapper.toResponseDTO(resolverSala(id));
    }

    public SalaResponseDTO crear(SalaRequestDTO dto) {
        validarDatosSala(dto);
        boolean activa = dto.activa() == null || dto.activa();
        Sala nuevaSala = SalaMapper.toModel(dto, activa);
        return SalaMapper.toResponseDTO(salaRepository.save(nuevaSala));
    }

    public SalaResponseDTO actualizar(Long id, SalaRequestDTO dto) {
        validarDatosSala(dto);
        Sala sala = resolverSala(id);
        SalaMapper.updateModel(sala, dto);
        return SalaMapper.toResponseDTO(salaRepository.update(sala));
    }

    public void eliminar(Long id) {
        resolverSala(id);
        reservaRepository.deleteBySalaId(id);
        salaRepository.deleteById(id);
    }

    // ---------- helpers privados ----------

    private Sala resolverSala(Long id) {
        return salaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada con id: " + id));
    }

    private void validarDatosSala(SalaRequestDTO dto) {
        if (dto.capacidad() != null && dto.capacidad() <= 0) {
            throw new RuntimeException("La capacidad de la sala debe ser mayor a cero");
        }
    }
}
