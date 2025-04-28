package com.parrot.api.puntoventa.controller;

import com.parrot.api.puntoventa.models.dto.MeseroEmailRequest;
import com.parrot.api.puntoventa.services.MeseroService;
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
@RequestMapping(value = "/meseros")
public class MeseroController {

    private final MeseroService meseroService;

    @Autowired
    public MeseroController(MeseroService meseroService){
        this.meseroService = meseroService;
    }

    @PostMapping(value = "/getEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getMeseroByEmail(@RequestBody MeseroEmailRequest meseroEmailRequest) {
        try {
            var response = meseroService.getMeseroByEmail(meseroEmailRequest.getEmail());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("Error al obtener el mesero por su email: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
