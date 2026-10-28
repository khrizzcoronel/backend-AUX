package com.app.auxiliar.services.implementacion;

import com.app.auxiliar.dtos.FacultadDto;
import com.app.auxiliar.entities.Facultad;
import com.app.auxiliar.repositories.IFacultadRepository;
import com.app.auxiliar.services.IFacultadService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@SuppressWarnings("null")
@Service
@RequiredArgsConstructor
public class ImplementacionFacultadService implements IFacultadService {
    private final IFacultadRepository facultadRepository;

    @Override
    public FacultadDto obtenerFacultadPorId(@NonNull Integer idFacultad) {
        Facultad facultad = facultadRepository.findById(idFacultad)
                .orElseThrow(() -> new RuntimeException("Facultad no encontrada con id: " + idFacultad));
        return convertirADto(facultad);
    }

    @Override
    public FacultadDto crearFacultad(FacultadDto facultadDto) {
        Facultad facultad = new Facultad();
        BeanUtils.copyProperties(facultadDto, facultad);
        Facultad facultadGuardada = facultadRepository.save(facultad);
        return convertirADto(facultadGuardada);
    }

    @Override
    public FacultadDto actualizarFacultad(@NonNull Integer idFacultad, @NonNull FacultadDto facultadDto) {
        Facultad facultadExistente = facultadRepository.findById(idFacultad)
                .orElseThrow(() -> new RuntimeException("Facultad no encontrada con id: " + idFacultad));
        BeanUtils.copyProperties(facultadDto, facultadExistente);
        Facultad facultadActualizada = facultadRepository.save(facultadExistente);
        return convertirADto(facultadActualizada);
    }

    @Override
    public FacultadDto eliminarFacultad(@NonNull Integer idFacultad) {
        Facultad facultadExistente = facultadRepository.findById(idFacultad)
                .orElseThrow(() -> new RuntimeException("Facultad no encontrada con id: " + idFacultad));
        facultadRepository.delete(facultadExistente);
        return convertirADto(facultadExistente);
    }

    public FacultadDto convertirADto(Facultad facultad) {
        FacultadDto facultadDto = new FacultadDto();
        BeanUtils.copyProperties(facultad, facultadDto);
        return facultadDto;
    }
}



