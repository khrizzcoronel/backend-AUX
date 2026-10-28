package com.app.auxiliar.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "info_estudiantes")
public class EstudianteInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estudiante")
    private Integer idEstudiante;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrera")
    private Carrera carrera;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_Periodo_actual")
    private Periodo currentPeriodo;

    @Column(name = "numero_matricula", unique = true)
    private String numeroMatricula;

    @Column(name = "paralelo")
    private Character paralelo;

    @Column(name = "jornada")
    private String jornada;

    @Column(name = "fecha_ingreso")
    private LocalDateTime fechaIngreso;

    @Column(name = "fecha_egreso")
    private LocalDate fechaEgreso;

    @Column(name = "estado_academico")
    private String estadoAcademico;

    @Column(name = "promedio_general", precision = 4, scale = 2)
    private BigDecimal promedioGeneral;

    @Column(name = "creditos_aprobados")
    private Integer creditosAprobados;

    @Column(name = "creditos_totales")
    private Integer creditosTotales;

    @Column(name = "matriculado")
    private Boolean matriculado;

    @Column(name = "es_becado")
    private Boolean esBecado;

    @Column(name = "tipo_beca")
    private String tipoBeca;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
}




