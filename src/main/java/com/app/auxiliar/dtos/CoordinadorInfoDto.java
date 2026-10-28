package com.app.auxiliar.dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CoordinadorInfoDto {
    private Integer idCoordinador;
    private Integer idUsuario;
    private Integer idCarrera;
    private String oficinaAtencion;
    private String horarioAtencion;
    private String extensionTelefonica;
    private LocalDate fechaNombramiento;
    private LocalDate fechaFinPeriodo;
    private Boolean estado;
    private String observaciones;
}




