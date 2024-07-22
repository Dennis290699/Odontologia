# Patrones de Base de Datos con Docker y PostgreSQL

Este repositorio proporciona un ejemplo de configuración y uso de PostgreSQL en un contenedor Docker para gestionar una base de datos de servicios, pacientes y citas.

## Creación del Contenedor Docker

Para crear y ejecutar un contenedor Docker con PostgreSQL, usa el siguiente comando:

```bash
docker run --name Patrones_Container -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin123 -e POSTGRES_DB=Brodents -p 5432:5432 -d postgres:latest
```

Este comando hará lo siguiente:
- Crea un contenedor llamado `Patrones_Container`.
- Establece el usuario: admin, contraseña: admin123 y base de datos inicial.
- Expone el puerto 5432 para acceder a la base de datos desde tu máquina local.
- Ejecuta PostgreSQL en segundo plano.

## Acceso al Contenedor

Para acceder a la consola de PostgreSQL dentro del contenedor, usa:

```bash
docker exec -it Patrones_Container psql -U admin -d Brodents
```

Esto abrirá la terminal de PostgreSQL para que puedas interactuar con la base de datos.

## Creación de Tablas

Para configurar las tablas necesarias, ejecuta los siguientes comandos SQL dentro de la consola de PostgreSQL:

```sql
CREATE TABLE tipos_servicio (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL
);

CREATE TABLE pacientes (
    id SERIAL PRIMARY KEY,
    cedula VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100)
);

CREATE TABLE citas (
    id SERIAL PRIMARY KEY,
    paciente_id INT REFERENCES pacientes(id),
    fecha_hora TIMESTAMP NOT NULL,
    tipo_servicio_id INT REFERENCES tipos_servicio(id),
    observaciones TEXT
);
```

## Pruebas de Inserción

Puedes probar la inserción de datos en las tablas con los siguientes comandos SQL:

```sql
-- Insertar tipos de servicio
INSERT INTO tipos_servicio (nombre, precio) VALUES ('Consulta General', 50.00);

-- Insertar paciente
INSERT INTO pacientes (cedula, nombre, telefono, email) VALUES ('1234567890', 'Juan Perez', '555-1234', 'juan@example.com');

-- Insertar cita
INSERT INTO citas (paciente_id, fecha_hora, tipo_servicio_id, observaciones) VALUES (1, '2024-07-21 10:00:00', 1, 'Primera consulta');

-- Consultar datos
SELECT * FROM tipos_servicio;
SELECT * FROM pacientes;
SELECT * FROM citas;
```

## Notas

- Asegúrate de tener Docker instalado y en funcionamiento en tu máquina.
- Puedes ajustar los valores predeterminados como el nombre de usuario, contraseña, y nombre de la base de datos según tus necesidades.
- Los datos de prueba son solo ejemplos y deben adaptarse a los requerimientos específicos de tu aplicación.

¡Listo! Ahora tienes todo lo necesario para comenzar a trabajar con PostgreSQL en Docker. Si tienes alguna pregunta o necesitas ayuda adicional, no dudes en abrir un issue en el repositorio.
