package com.parrot.api.puntoventa.services.impl;

import com.parrot.api.puntoventa.models.OrdenData;
import com.parrot.api.puntoventa.models.ReporteData;
import com.parrot.api.puntoventa.models.mysqldb.Articulo;
import com.parrot.api.puntoventa.models.mysqldb.Mesero;
import com.parrot.api.puntoventa.models.mysqldb.Orden;
import com.parrot.api.puntoventa.repositories.ArticuloRepository;
import com.parrot.api.puntoventa.repositories.MeseroRepository;
import com.parrot.api.puntoventa.repositories.OrdenRepository;
import com.parrot.api.puntoventa.services.ReporteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReporteServiceImpl  implements ReporteService {

    private final OrdenRepository ordenRepository;
    private final ArticuloRepository articuloRepository;
    private final MeseroRepository meseroRepository;

    @Override
    public List<ReporteData> getReporteByRangeDates(String fechaIni, String fechaFin) {

        List<Orden> ordenes = ordenRepository.findOrdenByRangeDates(fechaIni, fechaFin);

        // Agrupar por nombreArticulo y sumar cantidad y total
        Map<String, DoubleSummaryStatistics> resumenPorArticulo = ordenes.stream()
                .collect(Collectors.groupingBy(
                        orden -> getNameByIdArticulo(orden.getIdArticulo()),
                        Collectors.summarizingDouble(Orden::getCantidad)
                ));

        Map<String, DoubleSummaryStatistics> resumenPorPrecio = ordenes.stream()
                .collect(Collectors.groupingBy(
                        orden -> getNameByIdArticulo(orden.getIdArticulo()),
                        Collectors.summarizingDouble(Orden::getTotal)
                ));

        // Construir la lista de ReporteData
        return resumenPorArticulo.entrySet().stream()
                .map(entry -> {
                    String nombreArticulo = entry.getKey();
                    DoubleSummaryStatistics cantidadEstadisticas = resumenPorArticulo.get(nombreArticulo);
                    DoubleSummaryStatistics precioEstadisticas = resumenPorPrecio.get(nombreArticulo);

                // regresa el objeto ReporteData
                return ReporteData.builder()
                        .idArticulo(getArticuloByName(nombreArticulo))
                        .nombreArticulo(nombreArticulo)
                        .cantidadTotal(cantidadEstadisticas.getSum())
                        .precioTotal(precioEstadisticas.getSum())
                        .fechaOrden(ordenes.stream().filter(or->
                            getNameByIdArticulo(or.getIdArticulo()).equals(nombreArticulo))
                                .findFirst().orElseThrow().getFechaOrden())
                        .build();

        })
        .collect(Collectors.toList());
    }

    @Override
    public List<OrdenData> getOrdenesByArticulosAndFecha(int idArticulo, String fechaOrden) {
        List<Orden> ordenes = ordenRepository.findOrdenByIdArtAndFecha(idArticulo, fechaOrden);

        return ordenes.stream()
                .map(orden -> {
                    OrdenData or = new OrdenData();
                    or.setIdMesero(orden.getIdMesero());
                    or.setNombreMesero(getNombreMeseroById(orden.getIdMesero()));
                    or.setNombreComensal(orden.getNombreComensal());
                    or.setIdArticulo(orden.getIdArticulo());
                    or.setNombreArticulo(getNameByIdArticulo(orden.getIdArticulo()));
                    or.setCantidad(orden.getCantidad());
                    or.setCostoTotal(orden.getTotal());
                    or.setFechaOrden(orden.getFechaOrden());
                    return or;
                })
                .collect(Collectors.toList());
    }

    private String getNameByIdArticulo(int idArticulo) {
        return articuloRepository.getArtById(idArticulo)
                .map(Articulo::getNombreArticulo)
                .orElse("Nombre no encontrado");
    }
    private int getArticuloByName(String nombre) {
        return articuloRepository.getArticuloByName(nombre)
                .map(Articulo::getIdArticulo)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró ningún artículo con el nombre: " + nombre));
    }
    private String getNombreMeseroById(int idMesero){
        return meseroRepository.findById(idMesero)
                .map(Mesero::getNombreMesero)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró ningún mesero con el idMesero: " + idMesero));
    }

}
