package com.app.auxiliar.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "info_decanos")
public class DecanoInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_decano")
    private Integer idDecano;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_facultad")
    private Facultad facultad;

    @Column(name = "fecha_nombramiento")
    private LocalDateTime fechaNombramiento;

    @Column(name = "fecha_fin_Periodo")
    private LocalDate fechaFinPeriodo;

    @Column(name = "resolucion_nombramiento")
    private String resolucionNombramiento;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
}




