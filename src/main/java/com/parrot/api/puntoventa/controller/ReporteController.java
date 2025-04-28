package com.parrot.api.puntoventa.controller;

import com.parrot.api.puntoventa.models.dto.ReporteRequest;
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
@RequestMapping(value = "/getReports")
public class ReporteController {

    private final ReporteService reporteService;

    @Autowired
    public ReporteController(ReporteService reporteService){
        this.reporteService = reporteService;
    }

    @PostMapping(value = "/getReporteByRangeDates", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getReporteByRangeDates(@RequestBody ReporteRequest reporteRequest) {
        try {
            var response = reporteService.getReporteByRangeDates(reporteRequest.getFechaIni(), reporteRequest.getFechaFin());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("Error al obtener el reporte por sus fechas: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping(value = "/getReporteByArtFecha", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getOrdenesByArticulosAndFecha(@RequestBody ReporteRequest reporteRequest) {
        try {
            var response = reporteService.getOrdenesByArticulosAndFecha(reporteRequest.getIdArticulo(), reporteRequest.getFechaOrden());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("Error al obtener el reporte por el id articulo y fecha: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
