package com.spotify.sugeridos.controller;


import com.spotify.sugeridos.service.SugeridoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/api/v1/sugeridos")
public class SugeridoController {

    private final SugeridoService sugeridoService;

    public SugeridoController(SugeridoService sugeridoService) {
        this.sugeridoService = sugeridoService;
    }

    @GetMapping("/online/{popularidad}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<GetSugerenciaByPopularidadResponse> getSugerenciaByPopularidadOnline(@PathVariable String popularidad) {
        return ResponseEntity.ok(sugeridoService.getSugerenciaByPopularidadOnline(popularidad));
    }

    @GetMapping("/offline/{popularidad}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<GetSugerenciaByPopularidadResponse> getSugerenciaByPopularidadOffline(@PathVariable String popularidad) {
        return ResponseEntity.ok(sugeridoService.getSugerenciaByPopularidadOffline(popularidad));
    }
}
