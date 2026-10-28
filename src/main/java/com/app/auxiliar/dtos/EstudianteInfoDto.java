package com.app.auxiliar.dtos;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EstudianteInfoDto {
    private Integer idEstudiante;
    private Integer idUsuario;
    private Integer idCarrera;
    private Integer idPeriodoActual;
    private String numeroMatricula;
    private Character paralelo;
    private String jornada;
    private LocalDateTime fechaIngreso;
    private LocalDate fechaEgreso;
    private String estadoAcademico;
    private BigDecimal promedioGeneral;
    private Integer creditosAprobados;
    private Integer creditosTotales;
    private Boolean matriculado;
    private Boolean esBecado;
    private String tipoBeca;
    private String observaciones;
}




