package com.codigo.cowork.mapper;

import com.codigo.cowork.dto.SalaRequestDTO;
import com.codigo.cowork.dto.SalaResponseDTO;
import com.codigo.cowork.model.Sala;

public class SalaMapper {

    private SalaMapper() {
        throw new UnsupportedOperationException("Clase utilitaria — no instanciar");
    }

    public static Sala toModel(SalaRequestDTO dto, boolean activa) {
        return new Sala(null, dto.codigo(), dto.nombre(), dto.capacidad(), dto.ubicacion(), activa);
    }

    public static SalaResponseDTO toResponseDTO(Sala sala) {
        return new SalaResponseDTO(
                sala.getId(),
                sala.getCodigo(),
                sala.getNombre(),
                sala.getCapacidad(),
                sala.getUbicacion(),
                sala.isActiva(),
                buildDescripcionCorta(sala)
        );
    }

    public static void updateModel(Sala sala, SalaRequestDTO dto) {
        sala.setCodigo(dto.codigo());
        sala.setNombre(dto.nombre());
        sala.setCapacidad(dto.capacidad());
        sala.setUbicacion(dto.ubicacion());
        if (dto.activa() != null) {
            sala.setActiva(dto.activa());
        }
    }

    private static String buildDescripcionCorta(Sala sala) {
        return String.format("%s - %s (cap. %d)", sala.getCodigo(), sala.getNombre(), sala.getCapacidad());
    }
}
