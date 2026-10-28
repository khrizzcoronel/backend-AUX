package com.app.auxiliar.services.implementacion;

import com.app.auxiliar.dtos.ValidacionUsuarioResponseDto;
import com.app.auxiliar.entities.*;
import com.app.auxiliar.repositories.*;
import com.app.auxiliar.services.IValidacionUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ImplementacionValidacionUsuarioService implements IValidacionUsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private final IEstudianteInfoRepository estudianteInfoRepository;
    private final ICoordinadorInfoRepository coordinadorInfoRepository;
    private final IDecanoInfoRepository decanoInfoRepository;

    @Override
    @Transactional(readOnly = true)
    public ValidacionUsuarioResponseDto validarUsuarioPorCedula(String cedula) {
        // 1. Buscar usuario por cédula
        Usuario usuario = usuarioRepository.findByCedula(cedula)
                .orElseThrow(() -> new RuntimeException("No se encontró usuario con la cédula: " + cedula));

        // 2. Verificar que el usuario esté activo
        if (usuario.getEstado() == null || !usuario.getEstado()) {
            throw new RuntimeException("El usuario con cédula " + cedula + " no está activo en el sistema");
        }

        // 3. Construir respuesta según el rol
        ValidacionUsuarioResponseDto response = new ValidacionUsuarioResponseDto();

        // Información básica del usuario
        setUsuarioInfo(response, usuario);

        // Cargar información específica según el rol
        String rol = usuario.getRol();
        if (rol != null) {
            switch (rol.toLowerCase()) {
                case "estudiante":
                    loadEstudianteInfo(response, usuario);
                    break;
                case "coordinador":
                    loadCoordinadorInfo(response, usuario);
                    break;
                case "decano":
                    loadDecanoInfo(response, usuario);
                    break;
                default:
                    throw new RuntimeException("Rol no reconocido: " + rol);
            }
        }

        return response;
    }

    private void setUsuarioInfo(ValidacionUsuarioResponseDto response, Usuario usuario) {
        response.setIdUsuario(usuario.getIdUsuario());
        response.setCedula(usuario.getCedula());
        response.setNombres(usuario.getNombres());
        response.setApellidos(usuario.getApellidos());
        response.setCorreoPersonal(usuario.getCorreoPersonal());
        response.setCorreoInstitucional(usuario.getCorreoInstitucional());
        response.setTelefono(usuario.getTelefono());
        response.setFechaNacimiento(usuario.getFechaNacimiento());
        response.setGenero(usuario.getGenero());
        response.setDireccion(usuario.getDireccion());
        response.setRol(usuario.getRol());
        response.setEstadoUsuario(usuario.getEstado());
    }

    private void loadEstudianteInfo(ValidacionUsuarioResponseDto response, Usuario usuario) {
        EstudianteInfo estudianteInfo = estudianteInfoRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("No se encontró información de estudiante para la cédula: " + usuario.getCedula()));

        // Verificar que esté matriculado
        if (estudianteInfo.getMatriculado() == null || !estudianteInfo.getMatriculado()) {
            throw new RuntimeException("El estudiante con cédula " + usuario.getCedula() + " no está matriculado actualmente");
        }

        // Información del estudiante
        response.setIdEstudiante(estudianteInfo.getIdEstudiante());
        response.setNumeroMatricula(estudianteInfo.getNumeroMatricula());
        response.setParalelo(estudianteInfo.getParalelo());
        response.setJornada(estudianteInfo.getJornada());
        response.setFechaIngreso(estudianteInfo.getFechaIngreso());
        response.setFechaEgreso(estudianteInfo.getFechaEgreso());
        response.setEstadoAcademico(estudianteInfo.getEstadoAcademico());
        response.setPromedioGeneral(estudianteInfo.getPromedioGeneral());
        response.setCreditosAprobados(estudianteInfo.getCreditosAprobados());
        response.setCreditosTotales(estudianteInfo.getCreditosTotales());
        response.setMatriculado(estudianteInfo.getMatriculado());
        response.setEsBecado(estudianteInfo.getEsBecado());
        response.setTipoBeca(estudianteInfo.getTipoBeca());

        // Información de la carrera
        Carrera carrera = estudianteInfo.getCarrera();
        if (carrera != null) {
            setCarreraInfo(response, carrera);

            // Información de la facultad
            if (carrera.getFacultad() != null) {
                setFacultadInfo(response, carrera.getFacultad());
            }
        }

        // Información del Periodo actual
        Periodo currentPeriodo = estudianteInfo.getCurrentPeriodo();
        if (currentPeriodo != null) {
            response.setIdPeriodo(currentPeriodo.getIdPeriodo());
            response.setCodigoPeriodo(currentPeriodo.getCodigoPeriodo());
            response.setNombrePeriodo(currentPeriodo.getNombrePeriodo());
            response.setFechaInicioPeriodo(currentPeriodo.getFechaInicio());
            response.setFechaFinPeriodo(currentPeriodo.getFechaFin());
            response.setEsPeriodoActual(currentPeriodo.getEsPeriodoActual());
        }
    }

    private void loadCoordinadorInfo(ValidacionUsuarioResponseDto response, Usuario usuario) {
        CoordinadorInfo coordinadorInfo = coordinadorInfoRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("No se encontró información de coordinador para la cédula: " + usuario.getCedula()));

        // Verificar que esté activo como coordinador
        if (coordinadorInfo.getEstado() == null || !coordinadorInfo.getEstado()) {
            throw new RuntimeException("El coordinador con cédula " + usuario.getCedula() + " no está activo actualmente");
        }

        // Información del coordinador
        response.setIdCoordinador(coordinadorInfo.getIdCoordinador());
        response.setOficinaAtencion(coordinadorInfo.getOficinaAtencion());
        response.setHorarioAtencion(coordinadorInfo.getHorarioAtencion());
        response.setExtensionTelefonica(coordinadorInfo.getExtensionTelefonica());
        response.setFechaNombramientoCoordinador(coordinadorInfo.getFechaNombramiento());
        response.setFechaFinPeriodoCoordinador(coordinadorInfo.getFechaFinPeriodo());

        // Información de la carrera
        Carrera carrera = coordinadorInfo.getCarrera();
        if (carrera != null) {
            setCarreraInfo(response, carrera);

            // Información de la facultad
            if (carrera.getFacultad() != null) {
                setFacultadInfo(response, carrera.getFacultad());
            }
        }
    }

    private void loadDecanoInfo(ValidacionUsuarioResponseDto response, Usuario usuario) {
        DecanoInfo decanoInfo = decanoInfoRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("No se encontró información de decano para la cédula: " + usuario.getCedula()));

        // Verificar que esté activo como decano
        if (decanoInfo.getEstado() == null || !decanoInfo.getEstado()) {
            throw new RuntimeException("El decano con cédula " + usuario.getCedula() + " no está activo actualmente");
        }

        // Información del decano
        response.setIdDecano(decanoInfo.getIdDecano());
        response.setFechaNombramientoDecano(decanoInfo.getFechaNombramiento());
        response.setFechaFinPeriodoDecano(decanoInfo.getFechaFinPeriodo());
        response.setResolucionNombramiento(decanoInfo.getResolucionNombramiento());

        // Información de la facultad
        Facultad facultad = decanoInfo.getFacultad();
        if (facultad != null) {
            setFacultadInfo(response, facultad);
        }
    }

    private void setCarreraInfo(ValidacionUsuarioResponseDto response, Carrera carrera) {
        response.setIdCarrera(carrera.getIdCarrera());
        response.setNombreCarrera(carrera.getNombreCarrera());
        response.setCodigoCarrera(carrera.getCodigoCarrera());
        response.setDuracionSemestres(carrera.getDuracionSemestres());
        response.setModalidad(carrera.getModalidad());
        response.setTituloOtorga(carrera.getTituloOtorga());
    }

    private void setFacultadInfo(ValidacionUsuarioResponseDto response, Facultad facultad) {
        response.setIdFacultad(facultad.getIdFacultad());
        response.setNombreFacultad(facultad.getNombreFacultad());
        response.setCodigoFacultad(facultad.getCodigoFacultad());
        response.setUbicacionOficinaFacultad(facultad.getUbicacionOficina());
        response.setEmailFacultad(facultad.getEmailFacultad());
    }
}



