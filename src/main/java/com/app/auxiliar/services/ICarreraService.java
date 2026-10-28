package com.app.auxiliar.services;

import com.app.auxiliar.dtos.CarreraDto;

public interface ICarreraService {
    CarreraDto obtenerCarreraPorId(Integer idCarrera);
    CarreraDto crearCarrera(CarreraDto carreraDto);
    CarreraDto actualizarCarrera(Integer idCarrera, CarreraDto carreraDto);
    CarreraDto eliminarCarrera(Integer idCarrera);
}




