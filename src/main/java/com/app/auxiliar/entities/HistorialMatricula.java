package com.app.auxiliar.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "historial_matriculas", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_estudiante", "id_Periodo"})
})
public class HistorialMatricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Integer idHistorial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estudiante", nullable = false)
    private EstudianteInfo student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_Periodo", nullable = false)
    private Periodo periodo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrera")
    private Carrera carrera;

    @Column(name = "numero_semestre")
    private Integer numeroSemestre;

    @Column(name = "paralelo")
    private String paralelo;

    @Column(name = "fecha_matricula")
    private LocalDateTime fechaMatricula;

    @Column(name = "tipo_matricula")
    private String tipoMatricula;

    @Column(name = "estado_matricula")
    private String estadoMatricula;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
}




