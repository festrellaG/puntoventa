package com.parrot.api.puntoventa.repositories;

import com.parrot.api.puntoventa.models.mysqldb.Mesero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeseroRepository extends JpaRepository<Mesero, Integer> {

    @Query(value = "SELECT mes.* FROM sys.mesero mes WHERE mes.email = :email" , nativeQuery = true)
    Optional<Mesero> getMeseroByEmail(String email);
}
