package com.app.auxiliar.services.implementacion;

import com.app.auxiliar.dtos.CarreraDto;
import com.app.auxiliar.entities.Carrera;
import com.app.auxiliar.entities.Facultad;
import com.app.auxiliar.repositories.ICarreraRepository;
import com.app.auxiliar.repositories.IFacultadRepository;
import com.app.auxiliar.services.ICarreraService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@SuppressWarnings("null")
@Service
@RequiredArgsConstructor
public class ImplementacionCarreraService implements ICarreraService {
    private final ICarreraRepository carreraRepository;
    private final IFacultadRepository facultadRepository;

    @Override
    public CarreraDto obtenerCarreraPorId(@NonNull Integer idCarrera) {
        Carrera carrera = carreraRepository.findById(idCarrera)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada con id: " + idCarrera));
        return convertirADto(carrera);
    }

    @Override
    public CarreraDto crearCarrera(CarreraDto carreraDto) {
        Carrera carrera = new Carrera();
        mapearDtoAEntidad(carreraDto, carrera);
        Carrera carreraGuardada = carreraRepository.save(carrera);
        return convertirADto(carreraGuardada);
    }

    @Override
    public CarreraDto actualizarCarrera(@NonNull Integer idCarrera, @NonNull CarreraDto carreraDto) {
        Carrera carreraExistente = carreraRepository.findById(idCarrera)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada con id: " + idCarrera));
        mapearDtoAEntidad(carreraDto, carreraExistente);
        Carrera carreraActualizada = carreraRepository.save(carreraExistente);
        return convertirADto(carreraActualizada);
    }

    @Override
    public CarreraDto eliminarCarrera(@NonNull Integer idCarrera) {
        Carrera carreraExistente = carreraRepository.findById(idCarrera)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada con id: " + idCarrera));
        carreraRepository.delete(carreraExistente);
        return convertirADto(carreraExistente);
    }

    private void mapearDtoAEntidad(CarreraDto dto, Carrera entity) {
        entity.setNombreCarrera(dto.getNombreCarrera());
        entity.setCodigoCarrera(dto.getCodigoCarrera());
        entity.setDuracionSemestres(dto.getDuracionSemestres());
        entity.setModalidad(dto.getModalidad());
        entity.setTituloOtorga(dto.getTituloOtorga());
        entity.setEstado(dto.getEstado());

        if (dto.getIdFacultad() != null) {
            Facultad facultad = facultadRepository.findById(dto.getIdFacultad())
                    .orElseThrow(() -> new RuntimeException("Facultad no encontrada con id: " + dto.getIdFacultad()));
            entity.setFacultad(facultad);
        } else {
            entity.setFacultad(null);
        }
    }

    private CarreraDto convertirADto(Carrera carrera) {
        CarreraDto dto = new CarreraDto();
        dto.setIdCarrera(carrera.getIdCarrera());
        dto.setNombreCarrera(carrera.getNombreCarrera());
        dto.setCodigoCarrera(carrera.getCodigoCarrera());
        dto.setDuracionSemestres(carrera.getDuracionSemestres());
        dto.setModalidad(carrera.getModalidad());
        dto.setTituloOtorga(carrera.getTituloOtorga());
        dto.setEstado(carrera.getEstado());
        if (carrera.getFacultad() != null) {
            dto.setIdFacultad(carrera.getFacultad().getIdFacultad());
        }
        return dto;
    }
}




