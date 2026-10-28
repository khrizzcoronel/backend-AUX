package com.app.auxiliar.services;

import com.app.auxiliar.dtos.FacultadDto;

public interface IFacultadService {
    FacultadDto obtenerFacultadPorId(Integer idFacultad);
    FacultadDto crearFacultad(FacultadDto facultadDto);
    FacultadDto actualizarFacultad(Integer idFacultad, FacultadDto facultadDto);
    FacultadDto eliminarFacultad(Integer idFacultad);
}



