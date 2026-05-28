package com.codigo.cowork.mapper;

import com.codigo.cowork.dto.SalaRequestDTO;
import com.codigo.cowork.dto.SalaResponseDTO;
import com.codigo.cowork.model.Sala;

public class SalaMapper {

    private SalaMapper() {}

    public static Sala toModel(SalaRequestDTO dto, boolean activa) {
        Sala sala = new Sala();
        sala.setCodigo(dto.codigo());
        sala.setNombre(dto.nombre());
        sala.setCapacidad(dto.capacidad());
        sala.setUbicacion(dto.ubicacion());
        sala.setActiva(activa);
        return sala;
    }

    public static SalaResponseDTO toResponseDTO(Sala sala) {
        return new SalaResponseDTO(
                sala.getId(),
                sala.getCodigo(),
                sala.getNombre(),
                sala.getCapacidad(),
                sala.getUbicacion(),
                sala.isActiva(),
                crearDescripcionCorta(sala)
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

    private static String crearDescripcionCorta(Sala sala) {
        return sala.getCodigo() + " - " + sala.getNombre() + " (cap. " + sala.getCapacidad() + ")";
    }
}
