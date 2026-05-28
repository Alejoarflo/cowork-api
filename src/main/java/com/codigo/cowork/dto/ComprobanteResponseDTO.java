package com.codigo.cowork.dto;

public record ComprobanteResponseDTO(
        Long reservaId,
        String clienteId,
        String nombreArchivo,
        long tamanioBytes,
        String mensaje
) {
}
