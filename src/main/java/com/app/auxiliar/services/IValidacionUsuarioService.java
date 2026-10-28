package com.app.auxiliar.services;

import com.app.auxiliar.dtos.ValidacionUsuarioResponseDto;

public interface IValidacionUsuarioService {
    ValidacionUsuarioResponseDto validarUsuarioPorCedula(String cedula);
}




