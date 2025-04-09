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
@Table(name = "mesero", schema = "sys")
public class Mesero implements Serializable {

    @Id
    @JsonIgnore
    private int idMesero;
    @Column(name = "email")
    private String email;
    @Column(name = "nombre")
    private String nombreMesero;

}
