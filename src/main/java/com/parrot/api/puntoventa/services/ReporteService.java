package com.parrot.api.puntoventa.services;

import com.parrot.api.puntoventa.models.dto.OrdenData;
import com.parrot.api.puntoventa.models.dto.ReporteData;

import java.util.List;

public interface ReporteService {

    List<ReporteData> getReporteByRangeDates(String fechaIni, String fechaFin);

    List<OrdenData> getOrdenesByArticulosAndFecha(int idArticulo, String fechaOrden);
}
