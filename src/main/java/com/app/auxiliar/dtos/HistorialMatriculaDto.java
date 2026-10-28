package com.app.auxiliar.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HistorialMatriculaDto {
    private Integer idHistorial;
    private Integer idEstudiante;
    private Integer idPeriodo;
    private Integer idCarrera;
    private Integer numeroSemestre;
    private String paralelo;
    private LocalDateTime fechaMatricula;
    private String tipoMatricula;
    private String estadoMatricula;
    private String observaciones;
}




