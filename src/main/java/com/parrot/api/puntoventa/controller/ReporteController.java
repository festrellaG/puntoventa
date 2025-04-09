package com.parrot.api.puntoventa.controller;

import com.parrot.api.puntoventa.services.MeseroService;
import com.parrot.api.puntoventa.services.ReporteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    @Autowired
    public ReporteController(ReporteService reporteService){
        this.reporteService = reporteService;
    }

    @PostMapping(value = "/getEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getMeseroByEmail(@RequestBody String email) {
        try {
            var response = meseroService.getMeseroByEmail(email);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("Error al obtener el mesero por su email: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
