package com.app.auxiliar.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "carreras")
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrera")
    private Integer idCarrera;

    @Column(name = "nombre_carrera", nullable = false)
    private String nombreCarrera;

    @Column(name = "codigo_carrera", unique = true)
    private String codigoCarrera;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_facultad")
    private Facultad facultad;

    @Column(name = "duracion_semestres")
    private Integer duracionSemestres;

    @Column(name = "modalidad")
    private String modalidad;

    @Column(name = "titulo_otorga")
    private String tituloOtorga;

    @Column(name = "estado")
    private Boolean estado;
}




