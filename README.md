# cowork-api

Sistema de Reservas de Sala para CoworkLima S.A.C.

## Datos

- Alumno: xalej
- Curso: Spring Boot Core / Arquitectura Backend
- Version: 1.0.0
- Repositorio publico GitHub: https://github.com/Alejoarflo/cowork-api

## Como ejecutar

```bash
./mvnw spring-boot:run
```

En Windows tambien puede ejecutarse con:

```bash
mvnw.cmd spring-boot:run
```

La aplicacion levanta en:

```text
http://localhost:9090
```

## Endpoints

| Metodo | Ruta | Descripcion |
|---|---|---|
| GET | `/api/info` | Informacion de la aplicacion |
| GET | `/api/salas` | Listar todas las salas |
| GET | `/api/salas/{id}` | Obtener una sala por ID |
| POST | `/api/salas` | Crear una nueva sala |
| PUT | `/api/salas/{id}` | Actualizar una sala existente |
| DELETE | `/api/salas/{id}` | Eliminar una sala y sus reservas asociadas |
| POST | `/api/reservas` | Crear una nueva reserva |
| GET | `/api/reservas/{id}` | Obtener una reserva por ID |
| GET | `/api/reservas` | Listar reservas con filtros opcionales `estado`, `fecha` y `salaId` |
| GET | `/api/reservas/sala/{salaId}` | Listar reservas de una sala |
| PUT | `/api/reservas/{id}/estado?nuevoEstado=CONFIRMADA` | Cambiar el estado de una reserva |
| DELETE | `/api/reservas/{id}` | Eliminar una reserva |
| POST | `/api/reservas/{id}/comprobante` | Subir comprobante PDF con header `X-Cliente-Id` y form-data `archivo` |

## Ejemplos de request

POST `/api/salas`

```json
{
  "codigo": "SALA-B2",
  "nombre": "Sala Pacifico",
  "capacidad": 8,
  "ubicacion": "Piso 5"
}
```

POST `/api/reservas`

```json
{
  "salaId": 1,
  "responsable": "Maria Lopez",
  "email": "maria@cowork.pe",
  "fecha": "2026-06-10",
  "horaInicio": "09:00",
  "horaFin": "10:30"
}
```

## Responsabilidad de capas

`controller` recibe las peticiones HTTP, lee body, path variables, query params, headers y multipart, y delega al service. `service` contiene las reglas de negocio: estado inicial, estados validos, filtros combinados y borrado en cascada. `repository` guarda la informacion en memoria usando listas y contadores `AtomicLong`. `dto` define el contrato JSON de entrada y salida con records, y `mapper` centraliza las conversiones entre modelos internos y DTOs.

## Evidencias

Las capturas solicitadas estan en la carpeta `/docs`:

- `01-arranque-9090.png`
- `02-get-info.png`
- `03-post-sala.png`
- `04-post-reserva.png`
- `05-put-estado.png`
- `06-upload-pdf.png`
- `07-delete-reserva.png`
