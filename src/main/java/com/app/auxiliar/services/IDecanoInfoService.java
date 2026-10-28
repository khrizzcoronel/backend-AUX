package com.app.auxiliar.services;

import com.app.auxiliar.dtos.DecanoInfoDto;

public interface IDecanoInfoService {
    DecanoInfoDto obtenerDecanoInfoPorId(Integer idDecano);
    DecanoInfoDto crearDecanoInfo(DecanoInfoDto decanoInfoDto);
    DecanoInfoDto actualizarDecanoInfo(Integer idDecano, DecanoInfoDto decanoInfoDto);
    DecanoInfoDto eliminarDecanoInfo(Integer idDecano);
}




