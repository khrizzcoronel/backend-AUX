package com.app.auxiliar.services.implementacion;

import com.app.auxiliar.dtos.HistorialMatriculaDto;
import com.app.auxiliar.entities.Carrera;
import com.app.auxiliar.entities.HistorialMatricula;
import com.app.auxiliar.entities.Periodo;
import com.app.auxiliar.entities.EstudianteInfo;
import com.app.auxiliar.repositories.ICarreraRepository;
import com.app.auxiliar.repositories.IHistorialMatriculaRepository;
import com.app.auxiliar.repositories.IPeriodoRepository;
import com.app.auxiliar.repositories.IEstudianteInfoRepository;
import com.app.auxiliar.services.IHistorialMatriculaService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@SuppressWarnings("null")
@Service
@RequiredArgsConstructor
public class ImplementacionHistorialMatriculaService implements IHistorialMatriculaService {
    private final IHistorialMatriculaRepository historialMatriculaRepository;
    private final IEstudianteInfoRepository estudianteInfoRepository;
    private final IPeriodoRepository periodoRepository;
    private final ICarreraRepository carreraRepository;

    @Override
    public HistorialMatriculaDto obtenerHistorialMatriculaPorId(@NonNull Integer idHistorial) {
        HistorialMatricula historialMatricula = historialMatriculaRepository.findById(idHistorial)
                .orElseThrow(() -> new RuntimeException("HistorialMatricula no encontrado con id: " + idHistorial));
        return convertirADto(historialMatricula);
    }

    @Override
    public HistorialMatriculaDto crearHistorialMatricula(HistorialMatriculaDto historialMatriculaDto) {
        HistorialMatricula historialMatricula = new HistorialMatricula();
        mapearDtoAEntidad(historialMatriculaDto, historialMatricula);
        HistorialMatricula historialMatriculaGuardado = historialMatriculaRepository.save(historialMatricula);
        return convertirADto(historialMatriculaGuardado);
    }

    @Override
    public HistorialMatriculaDto actualizarHistorialMatricula(@NonNull Integer idHistorial, @NonNull HistorialMatriculaDto historialMatriculaDto) {
        HistorialMatricula historialMatriculaExistente = historialMatriculaRepository.findById(idHistorial)
                .orElseThrow(() -> new RuntimeException("HistorialMatricula no encontrado con id: " + idHistorial));
        mapearDtoAEntidad(historialMatriculaDto, historialMatriculaExistente);
        HistorialMatricula historialMatriculaActualizado = historialMatriculaRepository.save(historialMatriculaExistente);
        return convertirADto(historialMatriculaActualizado);
    }

    @Override
    public HistorialMatriculaDto eliminarHistorialMatricula(@NonNull Integer idHistorial) {
        HistorialMatricula historialMatriculaExistente = historialMatriculaRepository.findById(idHistorial)
                .orElseThrow(() -> new RuntimeException("HistorialMatricula no encontrado con id: " + idHistorial));
        historialMatriculaRepository.delete(historialMatriculaExistente);
        return convertirADto(historialMatriculaExistente);
    }

    private void mapearDtoAEntidad(HistorialMatriculaDto dto, HistorialMatricula entity) {
        entity.setNumeroSemestre(dto.getNumeroSemestre());
        entity.setParalelo(dto.getParalelo());
        entity.setFechaMatricula(dto.getFechaMatricula());
        entity.setTipoMatricula(dto.getTipoMatricula());
        entity.setEstadoMatricula(dto.getEstadoMatricula());
        entity.setObservaciones(dto.getObservaciones());

        if (dto.getIdEstudiante() != null) {
            EstudianteInfo student = estudianteInfoRepository.findById(dto.getIdEstudiante())
                    .orElseThrow(() -> new RuntimeException("EstudianteInfo no encontrado con id: " + dto.getIdEstudiante()));
            entity.setStudent(student);
        }

        if (dto.getIdPeriodo() != null) {
            Periodo periodo = periodoRepository.findById(dto.getIdPeriodo())
                    .orElseThrow(() -> new RuntimeException("Periodo no encontrado con id: " + dto.getIdPeriodo()));
            entity.setPeriodo(periodo);
        }

        if (dto.getIdCarrera() != null) {
            Carrera carrera = carreraRepository.findById(dto.getIdCarrera())
                    .orElseThrow(() -> new RuntimeException("Carrera no encontrada con id: " + dto.getIdCarrera()));
            entity.setCarrera(carrera);
        } else {
            entity.setCarrera(null);
        }
    }

    private HistorialMatriculaDto convertirADto(HistorialMatricula historialMatricula) {
        HistorialMatriculaDto dto = new HistorialMatriculaDto();
        dto.setIdHistorial(historialMatricula.getIdHistorial());
        dto.setNumeroSemestre(historialMatricula.getNumeroSemestre());
        dto.setParalelo(historialMatricula.getParalelo());
        dto.setFechaMatricula(historialMatricula.getFechaMatricula());
        dto.setTipoMatricula(historialMatricula.getTipoMatricula());
        dto.setEstadoMatricula(historialMatricula.getEstadoMatricula());
        dto.setObservaciones(historialMatricula.getObservaciones());
        if (historialMatricula.getStudent() != null) {
            dto.setIdEstudiante(historialMatricula.getStudent().getIdEstudiante());
        }
        if (historialMatricula.getPeriodo() != null) {
            dto.setIdPeriodo(historialMatricula.getPeriodo().getIdPeriodo());
        }
        if (historialMatricula.getCarrera() != null) {
            dto.setIdCarrera(historialMatricula.getCarrera().getIdCarrera());
        }
        return dto;
    }
}



