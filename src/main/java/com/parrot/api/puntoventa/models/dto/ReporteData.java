package com.parrot.api.puntoventa.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteData {
    private int idArticulo;
    private String nombreArticulo;
    private double cantidadTotal;
    private double precioTotal;
    private String fechaOrden;
    private double precio;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReporteData that = (ReporteData) o;
        return Objects.equals(nombreArticulo, that.nombreArticulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreArticulo);
    }
}
