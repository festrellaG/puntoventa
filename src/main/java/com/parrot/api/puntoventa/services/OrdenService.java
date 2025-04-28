package com.parrot.api.puntoventa.services;

import com.parrot.api.puntoventa.models.dto.OrdenRequest;

public interface OrdenService {

    void insertOrden(OrdenRequest ordenRequest);
}
