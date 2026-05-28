package com.codigo.cowork.controller;

import com.codigo.cowork.dto.InfoResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @GetMapping("/api/info")
    public InfoResponseDTO info() {
        return new InfoResponseDTO("cowork-api", "1.0.0", "xalej");
    }
}
