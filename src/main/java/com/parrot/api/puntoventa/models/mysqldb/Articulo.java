package com.parrot.api.puntoventa.models.mysqldb;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
@Data
@Entity
@Table(name = "articulo", schema = "sys")
public class Articulo implements Serializable {

    @Id
    @Column(name = "id_articulo")
    private int idArticulo;
    @Column(name = "nombre")
    private String nombreArticulo;
    @Column(name = "precio")
    private double precio;

}
