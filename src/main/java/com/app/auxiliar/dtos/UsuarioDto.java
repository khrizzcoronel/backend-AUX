package com.app.auxiliar.dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UsuarioDto {
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
    private Boolean estado;
}




