package com.codigo.cowork.controller;

import com.codigo.cowork.dto.SalaRequestDTO;
import com.codigo.cowork.dto.SalaResponseDTO;
import com.codigo.cowork.service.SalaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/salas")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @GetMapping
    public List<SalaResponseDTO> listar() {
        return salaService.listar();
    }

    @GetMapping("/{id}")
    public SalaResponseDTO obtenerPorId(@PathVariable Long id) {
        return salaService.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<SalaResponseDTO> crear(@RequestBody SalaRequestDTO requestDTO) {
        SalaResponseDTO responseDTO = salaService.crear(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    public SalaResponseDTO actualizar(@PathVariable Long id, @RequestBody SalaRequestDTO requestDTO) {
        return salaService.actualizar(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        salaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
