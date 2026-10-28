package com.app.auxiliar.services.implementacion;

import com.app.auxiliar.dtos.DecanoInfoDto;
import com.app.auxiliar.entities.DecanoInfo;
import com.app.auxiliar.entities.Facultad;
import com.app.auxiliar.entities.Usuario;
import com.app.auxiliar.repositories.IDecanoInfoRepository;
import com.app.auxiliar.repositories.IFacultadRepository;
import com.app.auxiliar.repositories.IUsuarioRepository;
import com.app.auxiliar.services.IDecanoInfoService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@SuppressWarnings("null")
@Service
@RequiredArgsConstructor
public class ImplementacionDecanoInfoService implements IDecanoInfoService {
    private final IDecanoInfoRepository decanoInfoRepository;
    private final IUsuarioRepository usuarioRepository;
    private final IFacultadRepository facultadRepository;

    @Override
    public DecanoInfoDto obtenerDecanoInfoPorId(@NonNull Integer idDecano) {
        DecanoInfo decanoInfo = decanoInfoRepository.findById(idDecano)
                .orElseThrow(() -> new RuntimeException("DecanoInfo no encontrado con id: " + idDecano));
        return convertirADto(decanoInfo);
    }

    @Override
    public DecanoInfoDto crearDecanoInfo(DecanoInfoDto decanoInfoDto) {
        DecanoInfo decanoInfo = new DecanoInfo();
        mapearDtoAEntidad(decanoInfoDto, decanoInfo);
        DecanoInfo decanoInfoGuardado = decanoInfoRepository.save(decanoInfo);
        return convertirADto(decanoInfoGuardado);
    }

    @Override
    public DecanoInfoDto actualizarDecanoInfo(@NonNull Integer idDecano, @NonNull DecanoInfoDto decanoInfoDto) {
        DecanoInfo decanoInfoExistente = decanoInfoRepository.findById(idDecano)
                .orElseThrow(() -> new RuntimeException("DecanoInfo no encontrado con id: " + idDecano));
        mapearDtoAEntidad(decanoInfoDto, decanoInfoExistente);
        DecanoInfo decanoInfoActualizado = decanoInfoRepository.save(decanoInfoExistente);
        return convertirADto(decanoInfoActualizado);
    }

    @Override
    public DecanoInfoDto eliminarDecanoInfo(@NonNull Integer idDecano) {
        DecanoInfo decanoInfoExistente = decanoInfoRepository.findById(idDecano)
                .orElseThrow(() -> new RuntimeException("DecanoInfo no encontrado con id: " + idDecano));
        decanoInfoRepository.delete(decanoInfoExistente);
        return convertirADto(decanoInfoExistente);
    }

    private void mapearDtoAEntidad(DecanoInfoDto dto, DecanoInfo entity) {
        entity.setFechaNombramiento(dto.getFechaNombramiento());
        entity.setFechaFinPeriodo(dto.getFechaFinPeriodo());
        entity.setResolucionNombramiento(dto.getResolucionNombramiento());
        entity.setEstado(dto.getEstado());
        entity.setObservaciones(dto.getObservaciones());

        if (dto.getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + dto.getIdUsuario()));
            entity.setUsuario(usuario);
        }

        if (dto.getIdFacultad() != null) {
            Facultad facultad = facultadRepository.findById(dto.getIdFacultad())
                    .orElseThrow(() -> new RuntimeException("Facultad no encontrada con id: " + dto.getIdFacultad()));
            entity.setFacultad(facultad);
        } else {
            entity.setFacultad(null);
        }
    }

    private DecanoInfoDto convertirADto(DecanoInfo decanoInfo) {
        DecanoInfoDto dto = new DecanoInfoDto();
        dto.setIdDecano(decanoInfo.getIdDecano());
        dto.setFechaNombramiento(decanoInfo.getFechaNombramiento());
        dto.setFechaFinPeriodo(decanoInfo.getFechaFinPeriodo());
        dto.setResolucionNombramiento(decanoInfo.getResolucionNombramiento());
        dto.setEstado(decanoInfo.getEstado());
        dto.setObservaciones(decanoInfo.getObservaciones());
        if (decanoInfo.getUsuario() != null) {
            dto.setIdUsuario(decanoInfo.getUsuario().getIdUsuario());
        }
        if (decanoInfo.getFacultad() != null) {
            dto.setIdFacultad(decanoInfo.getFacultad().getIdFacultad());
        }
        return dto;
    }
}




