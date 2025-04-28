package com.parrot.api.puntoventa.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdenRequest {
    private int idMesero;
    private String nombreComensal;
    private int idArticulo;
    private int cantidad;
    private double costoTotal;
    private String fechaOrden;
}
