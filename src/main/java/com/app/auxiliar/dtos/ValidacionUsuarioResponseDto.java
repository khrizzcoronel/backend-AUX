package com.app.auxiliar.dtos;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ValidacionUsuarioResponseDto {
    // Información del usuario
    private Integer idUsuario;
    private String cedula;
    private String nombres;
    private String apellidos;
    private String correoPersonal;
    private String correoInstitucional;
    private String telefono;
    private LocalDate fechaNacimiento;
    private Character genero;
    private String direccion;
    private String rol;
    private Boolean estadoUsuario;

    // Información de la facultad (para todos los roles)
    private Integer idFacultad;
    private String nombreFacultad;
    private String codigoFacultad;
    private String ubicacionOficinaFacultad;
    private String emailFacultad;

    // Información de la carrera (para estudiantes y coordinadores)
    private Integer idCarrera;
    private String nombreCarrera;
    private String codigoCarrera;
    private Integer duracionSemestres;
    private String modalidad;
    private String tituloOtorga;

    // Información específica de estudiante
    private Integer idEstudiante;
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

    // Información del Periodo actual (para estudiantes)
    private Integer idPeriodo;
    private String codigoPeriodo;
    private String nombrePeriodo;
    private LocalDate fechaInicioPeriodo;
    private LocalDate fechaFinPeriodo;
    private Boolean esPeriodoActual;

    // Información específica de coordinador
    private Integer idCoordinador;
    private String oficinaAtencion;
    private String horarioAtencion;
    private String extensionTelefonica;
    private LocalDate fechaNombramientoCoordinador;
    private LocalDate fechaFinPeriodoCoordinador;

    // Información específica de decano
    private Integer idDecano;
    private LocalDateTime fechaNombramientoDecano;
    private LocalDate fechaFinPeriodoDecano;
    private String resolucionNombramiento;
}




