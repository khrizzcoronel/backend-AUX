package com.app.auxiliar.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Periodos")
public class Periodo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Periodo")
    private Integer idPeriodo;

    @Column(name = "codigo_Periodo", nullable = false, unique = true)
    private String codigoPeriodo;

    @Column(name = "nombre_Periodo")
    private String nombrePeriodo;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "fecha_inicio_matriculas")
    private LocalDate fechaInicioMatriculas;

    @Column(name = "fecha_fin_matriculas")
    private LocalDate fechaFinMatriculas;

    @Column(name = "estado_activo")
    private Boolean estadoActivo;

    @Column(name = "es_Periodo_actual")
    private Boolean esPeriodoActual;
}



