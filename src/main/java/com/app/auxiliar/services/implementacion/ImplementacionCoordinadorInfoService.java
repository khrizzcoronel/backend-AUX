package com.app.auxiliar.services.implementacion;

import com.app.auxiliar.dtos.CoordinadorInfoDto;
import com.app.auxiliar.entities.Carrera;
import com.app.auxiliar.entities.CoordinadorInfo;
import com.app.auxiliar.entities.Usuario;
import com.app.auxiliar.repositories.ICarreraRepository;
import com.app.auxiliar.repositories.ICoordinadorInfoRepository;
import com.app.auxiliar.repositories.IUsuarioRepository;
import com.app.auxiliar.services.ICoordinadorInfoService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@SuppressWarnings("null")
@Service
@RequiredArgsConstructor
public class ImplementacionCoordinadorInfoService implements ICoordinadorInfoService {
    private final ICoordinadorInfoRepository coordinadorInfoRepository;
    private final IUsuarioRepository usuarioRepository;
    private final ICarreraRepository carreraRepository;

    @Override
    public CoordinadorInfoDto obtenerCoordinadorInfoPorId(@NonNull Integer idCoordinador) {
        CoordinadorInfo coordinadorInfo = coordinadorInfoRepository.findById(idCoordinador)
                .orElseThrow(() -> new RuntimeException("CoordinadorInfo no encontrado con id: " + idCoordinador));
        return convertirADto(coordinadorInfo);
    }

    @Override
    public CoordinadorInfoDto crearCoordinadorInfo(CoordinadorInfoDto coordinadorInfoDto) {
        CoordinadorInfo coordinadorInfo = new CoordinadorInfo();
        mapearDtoAEntidad(coordinadorInfoDto, coordinadorInfo);
        CoordinadorInfo coordinadorInfoGuardado = coordinadorInfoRepository.save(coordinadorInfo);
        return convertirADto(coordinadorInfoGuardado);
    }

    @Override
    public CoordinadorInfoDto actualizarCoordinadorInfo(@NonNull Integer idCoordinador, @NonNull CoordinadorInfoDto coordinadorInfoDto) {
        CoordinadorInfo coordinadorInfoExistente = coordinadorInfoRepository.findById(idCoordinador)
                .orElseThrow(() -> new RuntimeException("CoordinadorInfo no encontrado con id: " + idCoordinador));
        mapearDtoAEntidad(coordinadorInfoDto, coordinadorInfoExistente);
        CoordinadorInfo coordinadorInfoActualizado = coordinadorInfoRepository.save(coordinadorInfoExistente);
        return convertirADto(coordinadorInfoActualizado);
    }

    @Override
    public CoordinadorInfoDto eliminarCoordinadorInfo(@NonNull Integer idCoordinador) {
        CoordinadorInfo coordinadorInfoExistente = coordinadorInfoRepository.findById(idCoordinador)
                .orElseThrow(() -> new RuntimeException("CoordinadorInfo no encontrado con id: " + idCoordinador));
        coordinadorInfoRepository.delete(coordinadorInfoExistente);
        return convertirADto(coordinadorInfoExistente);
    }

    private void mapearDtoAEntidad(CoordinadorInfoDto dto, CoordinadorInfo entity) {
        entity.setOficinaAtencion(dto.getOficinaAtencion());
        entity.setHorarioAtencion(dto.getHorarioAtencion());
        entity.setExtensionTelefonica(dto.getExtensionTelefonica());
        entity.setFechaNombramiento(dto.getFechaNombramiento());
        entity.setFechaFinPeriodo(dto.getFechaFinPeriodo());
        entity.setEstado(dto.getEstado());
        entity.setObservaciones(dto.getObservaciones());

        if (dto.getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + dto.getIdUsuario()));
            entity.setUsuario(usuario);
        }

        if (dto.getIdCarrera() != null) {
            Carrera carrera = carreraRepository.findById(dto.getIdCarrera())
                    .orElseThrow(() -> new RuntimeException("Carrera no encontrada con id: " + dto.getIdCarrera()));
            entity.setCarrera(carrera);
        } else {
            entity.setCarrera(null);
        }
    }

    private CoordinadorInfoDto convertirADto(CoordinadorInfo coordinadorInfo) {
        CoordinadorInfoDto dto = new CoordinadorInfoDto();
        dto.setIdCoordinador(coordinadorInfo.getIdCoordinador());
        dto.setOficinaAtencion(coordinadorInfo.getOficinaAtencion());
        dto.setHorarioAtencion(coordinadorInfo.getHorarioAtencion());
        dto.setExtensionTelefonica(coordinadorInfo.getExtensionTelefonica());
        dto.setFechaNombramiento(coordinadorInfo.getFechaNombramiento());
        dto.setFechaFinPeriodo(coordinadorInfo.getFechaFinPeriodo());
        dto.setEstado(coordinadorInfo.getEstado());
        dto.setObservaciones(coordinadorInfo.getObservaciones());
        if (coordinadorInfo.getUsuario() != null) {
            dto.setIdUsuario(coordinadorInfo.getUsuario().getIdUsuario());
        }
        if (coordinadorInfo.getCarrera() != null) {
            dto.setIdCarrera(coordinadorInfo.getCarrera().getIdCarrera());
        }
        return dto;
    }
}




