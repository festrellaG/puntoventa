package com.parrot.api.puntoventa.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdenData {

    private int idMesero;
    private String nombreMesero;
    private String nombreComensal;
    private int idArticulo;
    private String nombreArticulo;
    private double cantidad;
    private double costoTotal;
    private String fechaOrden;
}
