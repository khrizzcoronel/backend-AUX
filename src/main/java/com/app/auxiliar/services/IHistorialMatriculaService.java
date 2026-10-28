package com.app.auxiliar.services;

import com.app.auxiliar.dtos.HistorialMatriculaDto;

public interface IHistorialMatriculaService {
    HistorialMatriculaDto obtenerHistorialMatriculaPorId(Integer idHistorial);
    HistorialMatriculaDto crearHistorialMatricula(HistorialMatriculaDto historialMatriculaDto);
    HistorialMatriculaDto actualizarHistorialMatricula(Integer idHistorial, HistorialMatriculaDto historialMatriculaDto);
    HistorialMatriculaDto eliminarHistorialMatricula(Integer idHistorial);
}




