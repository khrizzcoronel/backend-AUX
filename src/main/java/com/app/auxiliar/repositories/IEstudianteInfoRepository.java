package com.app.auxiliar.repositories;

import com.app.auxiliar.entities.EstudianteInfo;
import com.app.auxiliar.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IEstudianteInfoRepository extends JpaRepository<EstudianteInfo, Integer> {
    Optional<EstudianteInfo> findByUsuario(Usuario usuario);
}





