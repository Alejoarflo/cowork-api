package com.codigo.cowork.controller;

import com.codigo.cowork.dto.ComprobanteResponseDTO;
import com.codigo.cowork.dto.ReservaRequestDTO;
import com.codigo.cowork.dto.ReservaResponseDTO;
import com.codigo.cowork.service.ReservaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crear(@RequestBody ReservaRequestDTO requestDTO) {
        ReservaResponseDTO responseDTO = reservaService.crear(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ReservaResponseDTO obtenerPorId(@PathVariable Long id) {
        return reservaService.obtenerPorId(id);
    }

    @GetMapping
    public List<ReservaResponseDTO> listar(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(required = false) Long salaId) {
        return reservaService.listar(estado, fecha, salaId);
    }

    @GetMapping("/sala/{salaId}")
    public List<ReservaResponseDTO> listarPorSala(@PathVariable Long salaId) {
        return reservaService.listarPorSala(salaId);
    }

    @PutMapping("/{id}/estado")
    public ReservaResponseDTO cambiarEstado(@PathVariable Long id, @RequestParam String nuevoEstado) {
        return reservaService.cambiarEstado(id, nuevoEstado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reservaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/comprobante")
    public ComprobanteResponseDTO subirComprobante(
            @PathVariable Long id,
            @RequestParam("archivo") MultipartFile archivo,
            @RequestHeader("X-Cliente-Id") String clienteId) {
        return reservaService.registrarComprobante(id, archivo, clienteId);
    }
}
