package com.codigo.cowork.mapper;

import com.codigo.cowork.dto.ReservaRequestDTO;
import com.codigo.cowork.dto.ReservaResponseDTO;
import com.codigo.cowork.model.Reserva;

public class ReservaMapper {

    private ReservaMapper() {
        throw new UnsupportedOperationException("Clase utilitaria — no instanciar");
    }

    public static Reserva toModel(ReservaRequestDTO dto) {
        return new Reserva(
                null,
                dto.salaId(),
                dto.responsable(),
                dto.email(),
                dto.fecha(),
                dto.horaInicio(),
                dto.horaFin(),
                null,
                null
        );
    }

    public static ReservaResponseDTO toResponseDTO(Reserva reserva) {
        return new ReservaResponseDTO(
                reserva.getId(),
                reserva.getSalaId(),
                reserva.getResponsable(),
                reserva.getEmail(),
                reserva.getFecha(),
                reserva.getHoraInicio(),
                reserva.getHoraFin(),
                reserva.getEstado()
        );
    }
}
