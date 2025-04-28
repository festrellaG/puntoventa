package com.parrot.api.puntoventa.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteRequest {

    private String fechaIni;
    private String fechaFin;
    private int idArticulo;
    private String fechaOrden;
}
