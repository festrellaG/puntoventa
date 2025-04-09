package com.parrot.api.puntoventa.services;

import com.parrot.api.puntoventa.models.mysqldb.Mesero;

public interface MeseroService {

    Mesero getMeseroByEmail(String email);
}
