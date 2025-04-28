package com.parrot.api.puntoventa.repositories;

import com.parrot.api.puntoventa.models.mysqldb.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Integer> {

    @Query(value = "SELECT art.* FROM sys.articulo art WHERE art.id_articulo = :idArticulo" , nativeQuery = true)
    Optional<Articulo> getArtById(int idArticulo);

    @Query(value = "SELECT art.* FROM sys.articulo art WHERE art.nombre = :nombre" , nativeQuery = true)
    Optional<Articulo> getArticuloByName(String nombre);
}
