package com.app.auxiliar.services;

import com.app.auxiliar.dtos.CoordinadorInfoDto;

public interface ICoordinadorInfoService {
    CoordinadorInfoDto obtenerCoordinadorInfoPorId(Integer idCoordinador);
    CoordinadorInfoDto crearCoordinadorInfo(CoordinadorInfoDto coordinadorInfoDto);
    CoordinadorInfoDto actualizarCoordinadorInfo(Integer idCoordinador, CoordinadorInfoDto coordinadorInfoDto);
    CoordinadorInfoDto eliminarCoordinadorInfo(Integer idCoordinador);
}




