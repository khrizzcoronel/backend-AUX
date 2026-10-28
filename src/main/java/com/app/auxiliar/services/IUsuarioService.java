package com.app.auxiliar.services;

import com.app.auxiliar.dtos.UsuarioDto;

public interface IUsuarioService {
    UsuarioDto obtenerUsuarioPorId(Integer idUsuario);
    UsuarioDto crearUsuario(UsuarioDto usuarioDto);
    UsuarioDto actualizarUsuario(Integer idUsuario, UsuarioDto usuarioDto);
    UsuarioDto eliminarUsuario(Integer idUsuario);
}




