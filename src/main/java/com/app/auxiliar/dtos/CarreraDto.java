package com.app.auxiliar.dtos;

import lombok.Data;

@Data
public class CarreraDto {
    private Integer idCarrera;
    private String nombreCarrera;
    private String codigoCarrera;
    private Integer idFacultad;
    private Integer duracionSemestres;
    private String modalidad;
    private String tituloOtorga;
    private Boolean estado;
}




