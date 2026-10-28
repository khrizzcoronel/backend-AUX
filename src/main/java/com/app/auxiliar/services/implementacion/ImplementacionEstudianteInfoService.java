package com.app.auxiliar.services.implementacion;

import com.app.auxiliar.dtos.EstudianteInfoDto;
import com.app.auxiliar.entities.Carrera;
import com.app.auxiliar.entities.Periodo;
import com.app.auxiliar.entities.EstudianteInfo;
import com.app.auxiliar.entities.Usuario;
import com.app.auxiliar.repositories.ICarreraRepository;
import com.app.auxiliar.repositories.IPeriodoRepository;
import com.app.auxiliar.repositories.IEstudianteInfoRepository;
import com.app.auxiliar.repositories.IUsuarioRepository;
import com.app.auxiliar.services.IEstudianteInfoService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@SuppressWarnings("null")
@Service
@RequiredArgsConstructor
public class ImplementacionEstudianteInfoService implements IEstudianteInfoService {
    private final IEstudianteInfoRepository estudianteInfoRepository;
    private final IUsuarioRepository usuarioRepository;
    private final ICarreraRepository carreraRepository;
    private final IPeriodoRepository periodoRepository;

    @Override
    public EstudianteInfoDto obtenerEstudianteInfoPorId(@NonNull Integer idEstudiante) {
        EstudianteInfo estudianteInfo = estudianteInfoRepository.findById(idEstudiante)
                .orElseThrow(() -> new RuntimeException("EstudianteInfo no encontrado con id: " + idEstudiante));
        return convertirADto(estudianteInfo);
    }

    @Override
    public EstudianteInfoDto crearEstudianteInfo(EstudianteInfoDto estudianteInfoDto) {
        EstudianteInfo estudianteInfo = new EstudianteInfo();
        mapearDtoAEntidad(estudianteInfoDto, estudianteInfo);
        EstudianteInfo estudianteInfoGuardado = estudianteInfoRepository.save(estudianteInfo);
        return convertirADto(estudianteInfoGuardado);
    }

    @Override
    public EstudianteInfoDto actualizarEstudianteInfo(@NonNull Integer idEstudiante, @NonNull EstudianteInfoDto estudianteInfoDto) {
        EstudianteInfo estudianteInfoExistente = estudianteInfoRepository.findById(idEstudiante)
                .orElseThrow(() -> new RuntimeException("EstudianteInfo no encontrado con id: " + idEstudiante));
        mapearDtoAEntidad(estudianteInfoDto, estudianteInfoExistente);
        EstudianteInfo estudianteInfoActualizado = estudianteInfoRepository.save(estudianteInfoExistente);
        return convertirADto(estudianteInfoActualizado);
    }

    @Override
    public EstudianteInfoDto eliminarEstudianteInfo(@NonNull Integer idEstudiante) {
        EstudianteInfo estudianteInfoExistente = estudianteInfoRepository.findById(idEstudiante)
                .orElseThrow(() -> new RuntimeException("EstudianteInfo no encontrado con id: " + idEstudiante));
        estudianteInfoRepository.delete(estudianteInfoExistente);
        return convertirADto(estudianteInfoExistente);
    }

    private void mapearDtoAEntidad(EstudianteInfoDto dto, EstudianteInfo entity) {
        entity.setNumeroMatricula(dto.getNumeroMatricula());
        entity.setParalelo(dto.getParalelo());
        entity.setJornada(dto.getJornada());
        entity.setFechaIngreso(dto.getFechaIngreso());
        entity.setFechaEgreso(dto.getFechaEgreso());
        entity.setEstadoAcademico(dto.getEstadoAcademico());
        entity.setPromedioGeneral(dto.getPromedioGeneral());
        entity.setCreditosAprobados(dto.getCreditosAprobados());
        entity.setCreditosTotales(dto.getCreditosTotales());
        entity.setMatriculado(dto.getMatriculado());
        entity.setEsBecado(dto.getEsBecado());
        entity.setTipoBeca(dto.getTipoBeca());
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

        if (dto.getIdPeriodoActual() != null) {
            Periodo periodo = periodoRepository.findById(dto.getIdPeriodoActual())
                    .orElseThrow(() -> new RuntimeException("Periodo no encontrado con id: " + dto.getIdPeriodoActual()));
            entity.setCurrentPeriodo(periodo);
        } else {
            entity.setCurrentPeriodo(null);
        }
    }

    private EstudianteInfoDto convertirADto(EstudianteInfo estudianteInfo) {
        EstudianteInfoDto dto = new EstudianteInfoDto();
        dto.setIdEstudiante(estudianteInfo.getIdEstudiante());
        dto.setNumeroMatricula(estudianteInfo.getNumeroMatricula());
        dto.setParalelo(estudianteInfo.getParalelo());
        dto.setJornada(estudianteInfo.getJornada());
        dto.setFechaIngreso(estudianteInfo.getFechaIngreso());
        dto.setFechaEgreso(estudianteInfo.getFechaEgreso());
        dto.setEstadoAcademico(estudianteInfo.getEstadoAcademico());
        dto.setPromedioGeneral(estudianteInfo.getPromedioGeneral());
        dto.setCreditosAprobados(estudianteInfo.getCreditosAprobados());
        dto.setCreditosTotales(estudianteInfo.getCreditosTotales());
        dto.setMatriculado(estudianteInfo.getMatriculado());
        dto.setEsBecado(estudianteInfo.getEsBecado());
        dto.setTipoBeca(estudianteInfo.getTipoBeca());
        dto.setObservaciones(estudianteInfo.getObservaciones());
        if (estudianteInfo.getUsuario() != null) {
            dto.setIdUsuario(estudianteInfo.getUsuario().getIdUsuario());
        }
        if (estudianteInfo.getCarrera() != null) {
            dto.setIdCarrera(estudianteInfo.getCarrera().getIdCarrera());
        }
        if (estudianteInfo.getCurrentPeriodo() != null) {
            dto.setIdPeriodoActual(estudianteInfo.getCurrentPeriodo().getIdPeriodo());
        }
        return dto;
    }
}



