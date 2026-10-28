package com.app.auxiliar.repositories;

import com.app.auxiliar.entities.DecanoInfo;
import com.app.auxiliar.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IDecanoInfoRepository extends JpaRepository<DecanoInfo, Integer> {
    Optional<DecanoInfo> findByUsuario(Usuario usuario);
}





