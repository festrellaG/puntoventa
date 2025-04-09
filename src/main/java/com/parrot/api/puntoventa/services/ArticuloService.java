package com.parrot.api.puntoventa.services;

import com.parrot.api.puntoventa.models.mysqldb.Articulo;

import java.util.List;

public interface ArticuloService {

    List<Articulo> getAllArticulos();

    Articulo getArticuloById(int idArticulo);
}
