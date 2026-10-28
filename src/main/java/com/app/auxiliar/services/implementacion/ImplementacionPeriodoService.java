package com.app.auxiliar.services.implementacion;

import com.app.auxiliar.dtos.PeriodoDto;
import com.app.auxiliar.entities.Periodo;
import com.app.auxiliar.repositories.IPeriodoRepository;
import com.app.auxiliar.services.IPeriodoService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@SuppressWarnings("null")
@Service
@RequiredArgsConstructor
public class ImplementacionPeriodoService implements IPeriodoService {
    private final IPeriodoRepository periodoRepository;

    @Override
    public PeriodoDto obtenerPeriodoPorId(@NonNull Integer idPeriodo) {
        Periodo periodo = periodoRepository.findById(idPeriodo)
                .orElseThrow(() -> new RuntimeException("Periodo no encontrado con id: " + idPeriodo));
        return convertirADto(periodo);
    }

    @Override
    public PeriodoDto crearPeriodo(PeriodoDto periodoDto) {
        Periodo periodo = new Periodo();
        BeanUtils.copyProperties(periodoDto, periodo);
        Periodo periodoGuardado = periodoRepository.save(periodo);
        return convertirADto(periodoGuardado);
    }

    @Override
    public PeriodoDto actualizarPeriodo(@NonNull Integer idPeriodo, @NonNull PeriodoDto periodoDto) {
        Periodo periodoExistente = periodoRepository.findById(idPeriodo)
                .orElseThrow(() -> new RuntimeException("Periodo no encontrado con id: " + idPeriodo));
        BeanUtils.copyProperties(periodoDto, periodoExistente);
        Periodo periodoActualizado = periodoRepository.save(periodoExistente);
        return convertirADto(periodoActualizado);
    }

    @Override
    public PeriodoDto eliminarPeriodo(@NonNull Integer idPeriodo) {
        Periodo periodoExistente = periodoRepository.findById(idPeriodo)
                .orElseThrow(() -> new RuntimeException("Periodo no encontrado con id: " + idPeriodo));
        periodoRepository.delete(periodoExistente);
        return convertirADto(periodoExistente);
    }

    private PeriodoDto convertirADto(Periodo periodo) {
        PeriodoDto dto = new PeriodoDto();
        BeanUtils.copyProperties(periodo, dto);
        return dto;
    }
}



