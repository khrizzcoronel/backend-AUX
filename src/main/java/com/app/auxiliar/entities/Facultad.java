package com.app.auxiliar.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Data;

@Data
@Entity
@Table(name = "facultades")
public class Facultad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_facultad")
    private Integer idFacultad;

    @Column(name = "nombre_facultad")
    private String nombreFacultad;

    @Column(name = "codigo_facultad")
    private String codigoFacultad;

    @Column(name = "ubicacion_oficina")
    private String ubicacionOficina;

    @Column(name = "telefono_oficina")
    private String telefonoOficina;

    @Column(name = "email_facultad")
    private String emailFacultad;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Column(name = "estado")
    private Boolean estado;
}



