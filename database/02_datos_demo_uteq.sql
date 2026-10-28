-- Ejecutar en pgAdmin conectado a "sga-auxiliar01", despues de 01_crear_esquema.sql.
-- Facultad y carreras basadas en la oferta de UTEQ; personas, periodos,
-- codigos, correos y matriculas son ficticios. No contiene datos personales reales.
-- Puede ejecutarse de nuevo: reutiliza las filas DEMO y completa las faltantes.
BEGIN;

DO $demo$
DECLARE
    v_facultad_id integer;
    v_periodo_anterior integer;
    v_periodo_actual integer;
    v_carrera_id integer;
    v_usuario_id integer;
    v_estudiante_id integer;
    v_carrera_indice integer;
    v_semestre integer;
    v_cedula text;
    v_nombre text;
    v_apellido text;
    v_rol text;
    v_codigo_carrera text;
    v_carrera record;
    v_total integer;
    i integer;
BEGIN
    -- La entidad Facultad no declara unicidad en codigo_facultad.
    SELECT count(*) INTO v_total
    FROM public.facultades WHERE codigo_facultad = 'DEMO-FCC';
    IF v_total > 1 THEN
        RAISE EXCEPTION 'Hay mas de una facultad con codigo DEMO-FCC';
    END IF;

    IF v_total = 0 THEN
        INSERT INTO public.facultades
            (nombre_facultad, codigo_facultad, ubicacion_oficina, email_facultad, estado)
        VALUES
            ('Facultad de Ciencias de la Computación', 'DEMO-FCC',
             'Oficina ficticia - Quevedo', 'facultad.demo@example.test', true);
    END IF;

    SELECT id_facultad INTO STRICT v_facultad_id
    FROM public.facultades
    WHERE codigo_facultad = 'DEMO-FCC'
      AND nombre_facultad = 'Facultad de Ciencias de la Computación';

    INSERT INTO public.periodos
        (codigo_periodo, nombre_periodo, fecha_inicio, fecha_fin,
         fecha_inicio_matriculas, fecha_fin_matriculas, estado_activo, es_periodo_actual)
    VALUES
        ('DEMO-2025B', 'Período ficticio 2025 B', DATE '2025-09-01', DATE '2026-02-28',
         DATE '2025-08-15', DATE '2025-09-15', false, false),
        ('DEMO-2026B', 'Período ficticio 2026 B', DATE '2026-09-01', DATE '2027-02-28',
         DATE '2026-08-15', DATE '2026-09-15', true, true)
    ON CONFLICT (codigo_periodo) DO NOTHING;

    SELECT id_periodo INTO STRICT v_periodo_anterior
    FROM public.periodos
    WHERE codigo_periodo = 'DEMO-2025B'
      AND fecha_inicio = DATE '2025-09-01'
      AND fecha_fin = DATE '2026-02-28'
      AND es_periodo_actual = false;

    SELECT id_periodo INTO STRICT v_periodo_actual
    FROM public.periodos
    WHERE codigo_periodo = 'DEMO-2026B'
      AND fecha_inicio = DATE '2026-09-01'
      AND fecha_fin = DATE '2027-02-28'
      AND estado_activo = true
      AND es_periodo_actual = true;

    FOR v_carrera IN
        SELECT * FROM (VALUES
            (1, 'DEMO-SOF', 'Software', 10, 'Presencial', 'Ingeniero/a de Software'),
            (2, 'DEMO-TEL', 'Telemática', 8, 'Presencial', 'Ingeniero/a en Telemática'),
            (3, 'DEMO-TI', 'Tecnologías de la Información', 9, 'En Línea',
             'Ingeniero/a en Tecnologías de la Información'),
            (4, 'DEMO-SI', 'Sistemas de Información', 9, 'En Línea',
             'Ingeniero/a en Sistemas de Información')
        ) AS c(orden, codigo, nombre, semestres, modalidad, titulo)
    LOOP
        INSERT INTO public.carreras
            (nombre_carrera, codigo_carrera, id_facultad, duracion_semestres,
             modalidad, titulo_otorga, estado)
        VALUES
            (v_carrera.nombre, v_carrera.codigo, v_facultad_id, v_carrera.semestres,
             v_carrera.modalidad, v_carrera.titulo, true)
        ON CONFLICT (codigo_carrera) DO NOTHING;

        SELECT id_carrera INTO STRICT v_carrera_id
        FROM public.carreras
        WHERE codigo_carrera = v_carrera.codigo
          AND nombre_carrera = v_carrera.nombre
          AND id_facultad = v_facultad_id
          AND modalidad = v_carrera.modalidad
          AND duracion_semestres = v_carrera.semestres;
    END LOOP;

    -- DEMO000001..040: estudiantes; 041..044: coordinadores; 045: decano.
    FOR i IN 1..45 LOOP
        v_cedula := 'DEMO' || lpad(i::text, 6, '0');

        IF i <= 40 THEN
            v_rol := 'estudiante';
            v_nombre := (ARRAY['Ana','Bruno','Carla','Daniel','Elena',
                               'Fabián','Gabriela','Hugo','Isabel','Jorge'])[((i - 1) % 10) + 1];
            v_apellido := (ARRAY['Torres','Mora','Vera','Castro','Paredes',
                                 'Suárez','López','Rivas','Cruz','Aguilar'])[((i - 1) % 10) + 1]
                          || ' Demo' || lpad(i::text, 2, '0');
        ELSIF i <= 44 THEN
            v_rol := 'coordinador';
            v_nombre := (ARRAY['Lucía','Mateo','Paula','Andrés'])[i - 40];
            v_apellido := 'Coordinación Demo' || (i - 40)::text;
        ELSE
            v_rol := 'decano';
            v_nombre := 'Valeria';
            v_apellido := 'Decanato Demo';
        END IF;

        INSERT INTO public.usuarios
            (cedula, nombres, apellidos, correo_personal, correo_institucional,
             telefono, fecha_nacimiento, genero, direccion, rol, estado)
        VALUES
            (v_cedula, v_nombre, v_apellido,
             lower(v_cedula) || '@personal.example.test',
             lower(v_cedula) || '@campus.example.test',
             NULL,
             CASE WHEN i <= 40 THEN DATE '2002-01-01' + ((i - 1) * 47)
                  ELSE DATE '1980-01-01' + ((i - 41) * 640) END,
             CASE WHEN i IN (1,3,5,7,9,41,43,45) OR (i <= 40 AND i % 2 = 1)
                  THEN 'F' ELSE 'M' END,
             'Domicilio ficticio - Quevedo', v_rol, true)
        ON CONFLICT (cedula) DO NOTHING;

        SELECT id_usuario INTO STRICT v_usuario_id
        FROM public.usuarios
        WHERE cedula = v_cedula
          AND rol = v_rol
          AND estado = true
          AND correo_personal = lower(v_cedula) || '@personal.example.test'
          AND correo_institucional = lower(v_cedula) || '@campus.example.test';

        IF i <= 40 THEN
            v_carrera_indice := ((i - 1) / 10) + 1;
            v_codigo_carrera := (ARRAY['DEMO-SOF','DEMO-TEL','DEMO-TI','DEMO-SI'])[v_carrera_indice];
            SELECT id_carrera INTO STRICT v_carrera_id
            FROM public.carreras WHERE codigo_carrera = v_codigo_carrera;

            v_semestre := CASE WHEN ((i - 1) % 10) < 5 THEN 2 ELSE 1 END;

            INSERT INTO public.info_estudiantes
                (id_usuario, id_carrera, id_periodo_actual, numero_matricula,
                 paralelo, jornada, fecha_ingreso, estado_academico,
                 promedio_general, creditos_aprobados, creditos_totales,
                 matriculado, es_becado, tipo_beca, observaciones)
            VALUES
                (v_usuario_id, v_carrera_id, v_periodo_actual,
                 'DEMO-MAT-' || lpad(i::text, 3, '0'),
                 'A', CASE WHEN v_carrera_indice <= 2 THEN 'Matutina' ELSE 'Nocturna' END,
                 CASE WHEN v_semestre = 2 THEN TIMESTAMP '2025-09-01 08:00:00'
                      ELSE TIMESTAMP '2026-09-01 08:00:00' END,
                 'Regular',
                 CASE WHEN v_semestre = 2 THEN round((7.00 + (i % 25) * 0.10)::numeric, 2)
                      ELSE NULL END,
                 CASE WHEN v_semestre = 2 THEN 20 + (i % 6) ELSE 0 END,
                 CASE v_codigo_carrera WHEN 'DEMO-TEL' THEN 160
                                       WHEN 'DEMO-SOF' THEN 200 ELSE 180 END,
                 true, (i % 7 = 0),
                 CASE WHEN i % 7 = 0 THEN 'Beca académica ficticia' ELSE NULL END,
                 'DATOS DE PRUEBA')
            ON CONFLICT (id_usuario) DO NOTHING;

            SELECT id_estudiante INTO STRICT v_estudiante_id
            FROM public.info_estudiantes
            WHERE id_usuario = v_usuario_id
              AND id_carrera = v_carrera_id
              AND id_periodo_actual = v_periodo_actual
              AND numero_matricula = 'DEMO-MAT-' || lpad(i::text, 3, '0')
              AND matriculado = true;

            INSERT INTO public.historial_matriculas
                (id_estudiante, id_periodo, id_carrera, numero_semestre, paralelo,
                 fecha_matricula, tipo_matricula, estado_matricula, observaciones)
            VALUES
                (v_estudiante_id, v_periodo_actual, v_carrera_id, v_semestre, 'A',
                 TIMESTAMP '2026-09-15 09:00:00', 'Ordinaria', 'Activa', 'DATOS DE PRUEBA')
            ON CONFLICT (id_estudiante, id_periodo) DO NOTHING;

            IF v_semestre = 2 THEN
                INSERT INTO public.historial_matriculas
                    (id_estudiante, id_periodo, id_carrera, numero_semestre, paralelo,
                     fecha_matricula, tipo_matricula, estado_matricula, observaciones)
                VALUES
                    (v_estudiante_id, v_periodo_anterior, v_carrera_id, 1, 'A',
                     TIMESTAMP '2025-09-15 09:00:00', 'Ordinaria', 'Aprobada',
                     'DATOS DE PRUEBA')
                ON CONFLICT (id_estudiante, id_periodo) DO NOTHING;
            END IF;

        ELSIF i <= 44 THEN
            v_codigo_carrera := (ARRAY['DEMO-SOF','DEMO-TEL','DEMO-TI','DEMO-SI'])[i - 40];
            SELECT id_carrera INTO STRICT v_carrera_id
            FROM public.carreras WHERE codigo_carrera = v_codigo_carrera;

            INSERT INTO public.info_coordinadores
                (id_usuario, id_carrera, oficina_atencion, horario_atencion,
                 fecha_nombramiento, fecha_fin_periodo, estado, observaciones)
            VALUES
                (v_usuario_id, v_carrera_id, 'Oficina ficticia - carrera ' || (i - 40)::text,
                 'Lunes a viernes 09:00-12:00 (demo)', DATE '2026-01-15',
                 DATE '2027-12-31', true, 'DATOS DE PRUEBA')
            ON CONFLICT (id_usuario) DO NOTHING;

            PERFORM 1 FROM public.info_coordinadores
            WHERE id_usuario = v_usuario_id AND id_carrera = v_carrera_id AND estado = true;
            IF NOT FOUND THEN
                RAISE EXCEPTION 'Perfil de coordinador DEMO inconsistente: %', v_cedula;
            END IF;

        ELSE
            INSERT INTO public.info_decanos
                (id_usuario, id_facultad, fecha_nombramiento, fecha_fin_periodo,
                 resolucion_nombramiento, estado, observaciones)
            VALUES
                (v_usuario_id, v_facultad_id, TIMESTAMP '2025-07-01 09:00:00',
                 DATE '2028-06-30', 'DEMO-RES-001', true, 'DATOS DE PRUEBA')
            ON CONFLICT (id_usuario) DO NOTHING;

            PERFORM 1 FROM public.info_decanos
            WHERE id_usuario = v_usuario_id AND id_facultad = v_facultad_id AND estado = true;
            IF NOT FOUND THEN
                RAISE EXCEPTION 'Perfil de decano DEMO inconsistente: %', v_cedula;
            END IF;
        END IF;
    END LOOP;

    -- Si falta alguna fila o se encontro un conflicto, se revierte todo.
    SELECT count(*) INTO v_total
    FROM generate_series(1, 45) AS g(n)
    JOIN public.usuarios u ON u.cedula = 'DEMO' || lpad(g.n::text, 6, '0');
    IF v_total <> 45 THEN RAISE EXCEPTION 'Se esperaban 45 usuarios DEMO; hay %', v_total; END IF;

    SELECT count(*) INTO v_total
    FROM generate_series(1, 40) AS g(n)
    JOIN public.usuarios u ON u.cedula = 'DEMO' || lpad(g.n::text, 6, '0')
    JOIN public.info_estudiantes e ON e.id_usuario = u.id_usuario;
    IF v_total <> 40 THEN RAISE EXCEPTION 'Se esperaban 40 estudiantes DEMO; hay %', v_total; END IF;

    SELECT count(*) INTO v_total
    FROM generate_series(41, 44) AS g(n)
    JOIN public.usuarios u ON u.cedula = 'DEMO' || lpad(g.n::text, 6, '0')
    JOIN public.info_coordinadores co ON co.id_usuario = u.id_usuario;
    IF v_total <> 4 THEN RAISE EXCEPTION 'Se esperaban 4 coordinadores DEMO; hay %', v_total; END IF;

    SELECT count(*) INTO v_total
    FROM public.usuarios u
    JOIN public.info_decanos d ON d.id_usuario = u.id_usuario
    WHERE u.cedula = 'DEMO000045' AND d.id_facultad = v_facultad_id;
    IF v_total <> 1 THEN RAISE EXCEPTION 'Se esperaba 1 decano DEMO; hay %', v_total; END IF;

    SELECT count(*) INTO v_total
    FROM generate_series(1, 40) AS g(n)
    JOIN public.usuarios u ON u.cedula = 'DEMO' || lpad(g.n::text, 6, '0')
    JOIN public.info_estudiantes e ON e.id_usuario = u.id_usuario
    JOIN public.historial_matriculas h ON h.id_estudiante = e.id_estudiante
    WHERE h.id_periodo = v_periodo_actual;
    IF v_total <> 40 THEN RAISE EXCEPTION 'Se esperaban 40 matriculas actuales DEMO; hay %', v_total; END IF;

    SELECT count(*) INTO v_total
    FROM generate_series(1, 40) AS g(n)
    JOIN public.usuarios u ON u.cedula = 'DEMO' || lpad(g.n::text, 6, '0')
    JOIN public.info_estudiantes e ON e.id_usuario = u.id_usuario
    JOIN public.historial_matriculas h ON h.id_estudiante = e.id_estudiante
    WHERE h.id_periodo = v_periodo_anterior AND ((g.n - 1) % 10) < 5;
    IF v_total <> 20 THEN RAISE EXCEPTION 'Se esperaban 20 matriculas anteriores DEMO; hay %', v_total; END IF;

    RAISE NOTICE 'Datos DEMO listos: 1 facultad, 4 carreras, 2 periodos, 45 usuarios, 60 matriculas';
END
$demo$;

COMMIT;

-- Resumen visible en la salida de pgAdmin.
SELECT c.nombre_carrera,
       count(DISTINCT e.id_estudiante) AS estudiantes,
       count(DISTINCT co.id_coordinador) AS coordinadores
FROM public.carreras c
LEFT JOIN public.info_estudiantes e ON e.id_carrera = c.id_carrera
LEFT JOIN public.info_coordinadores co ON co.id_carrera = c.id_carrera
WHERE c.codigo_carrera IN ('DEMO-SOF','DEMO-TEL','DEMO-TI','DEMO-SI')
GROUP BY c.nombre_carrera
ORDER BY c.nombre_carrera;
