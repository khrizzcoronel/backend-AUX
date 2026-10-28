package com.app.auxiliar.services.implementacion;

import com.app.auxiliar.dtos.UsuarioDto;
import com.app.auxiliar.entities.Usuario;
import com.app.auxiliar.repositories.IUsuarioRepository;
import com.app.auxiliar.services.IUsuarioService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@SuppressWarnings("null")
@Service
@RequiredArgsConstructor
public class ImplementacionUsuarioService implements IUsuarioService {
    private final IUsuarioRepository usuarioRepository;

    @Override
    public UsuarioDto obtenerUsuarioPorId(@NonNull Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + idUsuario));
        return convertirADto(usuario);
    }

    @Override
    public UsuarioDto crearUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDto, usuario);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return convertirADto(usuarioGuardado);
    }

    @Override
    public UsuarioDto actualizarUsuario(@NonNull Integer idUsuario, @NonNull UsuarioDto usuarioDto) {
        Usuario usuarioExistente = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + idUsuario));
        BeanUtils.copyProperties(usuarioDto, usuarioExistente);
        Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);
        return convertirADto(usuarioActualizado);
    }

    @Override
    public UsuarioDto eliminarUsuario(@NonNull Integer idUsuario) {
        Usuario usuarioExistente = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + idUsuario));
        usuarioRepository.delete(usuarioExistente);
        return convertirADto(usuarioExistente);
    }

    private UsuarioDto convertirADto(Usuario usuario) {
        UsuarioDto dto = new UsuarioDto();
        BeanUtils.copyProperties(usuario, dto);
        return dto;
    }
}




