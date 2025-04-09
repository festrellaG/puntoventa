package com.parrot.api.puntoventa.services.impl;

import com.parrot.api.puntoventa.models.mysqldb.Articulo;
import com.parrot.api.puntoventa.repositories.ArticuloRepository;
import com.parrot.api.puntoventa.services.ArticuloService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticuloServiceImpl implements ArticuloService {

    private final ArticuloRepository articuloRepository;

    @Override
    public List<Articulo> getAllArticulos() {
        return articuloRepository.findAll();
    }

    @Override
    public Articulo getArticuloById(int idArticulo){
        return articuloRepository.getArtById(idArticulo)
                .orElseThrow(() -> new IllegalArgumentException("Artículo no encontrado con ID: " + idArticulo));
    }
}
