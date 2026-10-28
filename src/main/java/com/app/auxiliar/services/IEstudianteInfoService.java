package com.app.auxiliar.services;

import com.app.auxiliar.dtos.EstudianteInfoDto;

public interface IEstudianteInfoService {
    EstudianteInfoDto obtenerEstudianteInfoPorId(Integer idEstudiante);
    EstudianteInfoDto crearEstudianteInfo(EstudianteInfoDto estudianteInfoDto);
    EstudianteInfoDto actualizarEstudianteInfo(Integer idEstudiante, EstudianteInfoDto estudianteInfoDto);
    EstudianteInfoDto eliminarEstudianteInfo(Integer idEstudiante);
}




