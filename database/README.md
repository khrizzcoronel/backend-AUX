# Base de datos auxiliar para pgAdmin

1. Conectarse a la base administrativa `postgres` y ejecutar `00_crear_base.sql`.
2. Abrir Query Tool sobre la nueva base `sga-auxiliar01` y ejecutar `01_crear_esquema.sql`.
3. Para cargar datos ficticios de la Facultad de Ciencias de la Computación de UTEQ, ejecutar `02_datos_demo_uteq.sql` en `sga-auxiliar01`.

Los dos primeros scripts crean una base vacia con las ocho tablas, claves primarias, claves foraneas y restricciones `UNIQUE` declaradas por las entidades JPA. El tercero carga un conjunto opcional de demostracion: 1 facultad, 4 carreras, 2 periodos ficticios, 40 estudiantes, 4 coordinadores, 1 decano y 60 matriculas. Utiliza identificadores `DEMO`, correos `example.test` y ningun telefono real. Se puede volver a ejecutar sin duplicar las filas de demostracion.

Las carreras y modalidades se basan en la oferta de UTEQ; los perfiles y periodos son completamente ficticios. Fuentes: https://www.uteq.edu.ec/es/comunicacion/noticia/reunion-estrategica-en-la-uteq-anuncia-creacion-de-nueva-facultad y https://www.uteq.edu.ec/grado/carreras.

Para consultar el endpoint auxiliar tras cargar los datos, usar por ejemplo `DEMO000001` (estudiante de Software), `DEMO000011` (estudiante de Telematica), `DEMO000041` (coordinador de Software) o `DEMO000045` (decano). Los correos `example.test` no reciben mensajes reales.

El registro posterior de estas personas en el backend principal requiere que su base tenga los roles, la facultad, las carreras y los periodos con los mismos nombres/codigos de demostracion. Este script solo inserta datos en la base auxiliar.

El backend usa el esquema `public` y se conecta a `sga-auxiliar01` segun `src/main/resources/application.properties`. Ejecutar el segundo script sobre una base nueva: sus sentencias `CREATE TABLE` detectan una tabla preexistente en lugar de modificarla.
