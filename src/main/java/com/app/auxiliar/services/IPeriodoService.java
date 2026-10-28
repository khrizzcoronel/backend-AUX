package com.app.auxiliar.services;

import com.app.auxiliar.dtos.PeriodoDto;

public interface IPeriodoService {
    PeriodoDto obtenerPeriodoPorId(Integer idPeriodo);
    PeriodoDto crearPeriodo(PeriodoDto periodoDto);
    PeriodoDto actualizarPeriodo(Integer idPeriodo, PeriodoDto periodoDto);
    PeriodoDto eliminarPeriodo(Integer idPeriodo);
}



