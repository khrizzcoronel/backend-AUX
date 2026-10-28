package com.app.auxiliar.repositories;

import com.app.auxiliar.entities.CoordinadorInfo;
import com.app.auxiliar.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ICoordinadorInfoRepository extends JpaRepository<CoordinadorInfo, Integer> {
    Optional<CoordinadorInfo> findByUsuario(Usuario usuario);
}





