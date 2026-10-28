package com.app.auxiliar.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "info_coordinadores")
public class CoordinadorInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coordinador")
    private Integer idCoordinador;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrera")
    private Carrera carrera;

    @Column(name = "oficina_atencion")
    private String oficinaAtencion;

    @Column(name = "horario_atencion")
    private String horarioAtencion;

    @Column(name = "extension_telefonica")
    private String extensionTelefonica;

    @Column(name = "fecha_nombramiento")
    private LocalDate fechaNombramiento;

    @Column(name = "fecha_fin_Periodo")
    private LocalDate fechaFinPeriodo;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
}




