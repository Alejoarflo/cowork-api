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
        Sala sala = buscarSala(id);
        return SalaMapper.toResponseDTO(sala);
    }

    public SalaResponseDTO crear(SalaRequestDTO requestDTO) {
        boolean activa = requestDTO.activa() == null || requestDTO.activa();
        Sala sala = SalaMapper.toModel(requestDTO, activa);
        Sala salaGuardada = salaRepository.save(sala);
        return SalaMapper.toResponseDTO(salaGuardada);
    }

    public SalaResponseDTO actualizar(Long id, SalaRequestDTO requestDTO) {
        Sala sala = buscarSala(id);
        SalaMapper.updateModel(sala, requestDTO);
        Sala salaActualizada = salaRepository.update(sala);
        return SalaMapper.toResponseDTO(salaActualizada);
    }

    public void eliminar(Long id) {
        buscarSala(id);
        reservaRepository.deleteBySalaId(id);
        salaRepository.deleteById(id);
    }

    private Sala buscarSala(Long id) {
        return salaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe una sala con id " + id));
    }
}
