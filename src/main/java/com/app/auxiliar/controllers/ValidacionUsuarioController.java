package com.app.auxiliar.controllers;

import com.app.auxiliar.dtos.ValidacionUsuarioResponseDto;
import com.app.auxiliar.services.IValidacionUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/validation")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ValidacionUsuarioController {

    private final IValidacionUsuarioService validacionUsuarioService;
    
    @GetMapping("/usuario/{cedula}")
    public ResponseEntity<ValidacionUsuarioResponseDto> validarUsuarioPorCedula(@PathVariable String cedula) {
        ValidacionUsuarioResponseDto response = validacionUsuarioService.validarUsuarioPorCedula(cedula);
        return ResponseEntity.ok(response);
    }
}




