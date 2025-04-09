package com.parrot.api.puntoventa.models.mysqldb;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orden", schema = "sys")
public class Orden implements Serializable {

    @Id
    @JsonIgnore
    private int idOrden;
    @Column(name = "id_mesero")
    private int idMesero;
    @Column(name = "nombre_comensal")
    private String nombreComensal;
    @Column(name = "id_articulo")
    private int idArticulo;
    @Column(name = "cantidad")
    private double cantidad;
    @Column(name = "total")
    private double total;
    @Column(name = "fecha_orden")
    private String fechaOrden;

}
