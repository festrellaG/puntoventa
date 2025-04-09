package com.parrot.api.puntoventa.repositories;

import com.parrot.api.puntoventa.models.mysqldb.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Integer> {

    @Query(value = "SELECT o.* FROM sys.orden o WHERE o.fecha_orden BETWEEN :dateIni AND :dateFin", nativeQuery = true)
    List<Orden> findOrdenByRangeDates(String dateIni, String dateFin);

    @Query(value = "SELECT o.* FROM sys.orden o WHERE o.id_articulo = :idArticulo and o.fecha_orden = :fechaOrden", nativeQuery = true)
    List<Orden> findOrdenByIdArtAndFecha(int idArticulo, String fechaOrden);
}
