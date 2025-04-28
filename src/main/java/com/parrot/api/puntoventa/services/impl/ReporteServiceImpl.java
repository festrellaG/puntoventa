package com.parrot.api.puntoventa.services.impl;

import com.parrot.api.puntoventa.models.dto.OrdenData;
import com.parrot.api.puntoventa.models.dto.ReporteData;
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
                        .fechaOrden(obtenerFechaOrdenPorArticulo(ordenes, nombreArticulo))
                        .precio(getPrecioByName(nombreArticulo))
                        .build();

        })
        .collect(Collectors.toList());
    }

    private String obtenerFechaOrdenPorArticulo(List<Orden> ordenes, String nombreArticulo) {
        return ordenes.stream()
                .filter(or -> getNameByIdArticulo(or.getIdArticulo()).equals(nombreArticulo))
                .findFirst()
                .orElseThrow()
                .getFechaOrden();
    }

    @Override
    public List<OrdenData> getOrdenesByArticulosAndFecha(int idArticulo, String fechaOrden) {
        return Optional.ofNullable(ordenRepository.findOrdenByIdArtAndFecha(idArticulo, fechaOrden))
                .orElse(Collections.emptyList())
                .stream()
                .map(this::mapToOrdenData)
                .collect(Collectors.toList());
    }

    private OrdenData mapToOrdenData(Orden orden) {
        return OrdenData.builder()
                .idMesero(orden.getIdMesero())
                .nombreMesero(getNombreMeseroById(orden.getIdMesero()))
                .nombreComensal(orden.getNombreComensal())
                .idArticulo(orden.getIdArticulo())
                .nombreArticulo(getNameByIdArticulo(orden.getIdArticulo()))
                .cantidad(orden.getCantidad())
                .costoTotal(orden.getTotal())
                .fechaOrden(orden.getFechaOrden())
                .build();
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
    private double getPrecioByName(String nombre) {
        return articuloRepository.getArticuloByName(nombre)
                .map(Articulo::getPrecio)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró ningún artículo con el nombre: " + nombre));
    }
    private String getNombreMeseroById(int idMesero){
        return meseroRepository.findById(idMesero)
                .map(Mesero::getNombreMesero)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró ningún mesero con el idMesero: " + idMesero));
    }

}
