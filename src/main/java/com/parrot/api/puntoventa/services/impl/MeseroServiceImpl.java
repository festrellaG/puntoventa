package com.parrot.api.puntoventa.services.impl;

import com.parrot.api.puntoventa.models.mysqldb.Articulo;
import com.parrot.api.puntoventa.models.mysqldb.Mesero;
import com.parrot.api.puntoventa.repositories.MeseroRepository;
import com.parrot.api.puntoventa.services.MeseroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeseroServiceImpl implements MeseroService {

    private final MeseroRepository meseroRepository;

    @Override
    public Mesero getMeseroByEmail(String email) {
        return meseroRepository.getMeseroByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("email no encontrado: " + email));
    }
}
