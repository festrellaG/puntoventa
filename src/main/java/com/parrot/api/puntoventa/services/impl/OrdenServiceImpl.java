package com.parrot.api.puntoventa.services.impl;

import com.parrot.api.puntoventa.models.dto.OrdenRequest;
import com.parrot.api.puntoventa.models.mysqldb.Articulo;
import com.parrot.api.puntoventa.models.mysqldb.Orden;
import com.parrot.api.puntoventa.repositories.ArticuloRepository;
import com.parrot.api.puntoventa.repositories.OrdenRepository;
import com.parrot.api.puntoventa.services.OrdenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdenServiceImpl implements OrdenService {

    private final OrdenRepository ordenRepository;
    private final ArticuloRepository articuloRepository;

    @Override
    public void insertOrden(OrdenRequest ordenRequest) {
        Articulo articulo = getArticuloById(ordenRequest.getIdArticulo());

        Orden o = new Orden();
        o.setIdMesero(ordenRequest.getIdMesero());
        o.setNombreComensal(ordenRequest.getNombreComensal());
        o.setIdArticulo(articulo.getIdArticulo());
        o.setCantidad(ordenRequest.getCantidad());
        o.setTotal(ordenRequest.getCantidad() * articulo.getPrecio());
        o.setFechaOrden(ordenRequest.getFechaOrden());

        ordenRepository.save(o);
    }

    private Articulo getArticuloById(int idArticulo){
        return articuloRepository.getArtById(idArticulo)
                .orElseThrow(() -> new IllegalArgumentException("Artículo no encontrado con ID: " + idArticulo));
    }
}
