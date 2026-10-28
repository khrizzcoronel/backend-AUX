# SGTE — Backend auxiliar

API REST que representa la fuente académica externa para SGTE. Conserva perfiles de estudiantes, coordinadores y decanos, carreras, facultades, períodos e historial de matrículas. El frontend consulta este servicio para validar una cédula antes de solicitar el registro en el backend principal.

## Tecnología

Java 25, Spring Boot 3.5, Maven y PostgreSQL.

## Base de datos

La aplicación usa `sga-auxiliar01` en PostgreSQL y valida el esquema al iniciar (`ddl-auto=validate`). Los scripts para crear la base, el esquema y los perfiles ficticios de UTEQ están en [`database/`](database/README.md).

## Desarrollo local

1. Crear la base y el esquema siguiendo `database/README.md`.
2. Copiar `src/main/resources/application.example.properties` como `application.properties` y definir `DB_PASSWORD`. El archivo local queda excluido de Git.
3. Iniciar la aplicación:

```bash
./mvnw spring-boot:run
```

La API escucha en `http://localhost:9090`. La validación de una persona se consulta en `GET /api/validation/usuario/{cedula}`. Los registros `DEMO` del script SQL sirven para pruebas locales y usan correos `example.test`.
