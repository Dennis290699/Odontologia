# Patrones de Base de Datos con Docker y PostgreSQL

Este repositorio proporciona un ejemplo de configuración y uso de PostgreSQL en un contenedor Docker para gestionar una base de datos de servicios, pacientes, horarios y citas.

## Guía de Instalación

### Creación del Contenedor Docker

Para crear y ejecutar un contenedor Docker con PostgreSQL, usa el siguiente comando:

```bash
docker run --name NewPatrones_Container -e POSTGRES_USER=newadmin -e POSTGRES_PASSWORD=newadmin123 -e POSTGRES_DB=NewBrodents -p 5433:5432 -d postgres:latest
```

Este comando hará lo siguiente:
- Creará un contenedor llamado `NewPatrones_Container`.
- Establecerá el usuario como `newadmin`, la contraseña como `newadmin123` y la base de datos inicial como `NewBrodents`.
- Expondrá el puerto 5433 para acceder a la base de datos desde tu máquina local.
- Ejecutará PostgreSQL en segundo plano.

### Acceso al Contenedor

Para acceder a la consola de PostgreSQL dentro del contenedor, usa:

```bash
docker exec -it NewPatrones_Container psql -U newadmin -d NewBrodents
```

Esto abrirá la terminal de PostgreSQL para que puedas interactuar con la base de datos.

### Creación de Tablas

Para configurar las tablas necesarias, ejecuta los siguientes comandos SQL dentro de la consola de PostgreSQL:

```sql
CREATE TABLE servicios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL
);

CREATE TABLE pacientes (
    cedula VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    telefono VARCHAR(15) NOT NULL
);

CREATE TABLE horarios (
    numero SERIAL PRIMARY KEY,
    horario TIME NOT NULL
);

CREATE TABLE citas (
    id SERIAL PRIMARY KEY,
    cedula_paciente VARCHAR(20) NOT NULL,
    id_servicio INT NOT NULL,
    id_horario INT NOT NULL,
    fecha DATE NOT NULL,
    FOREIGN KEY (cedula_paciente) REFERENCES pacientes(cedula),
    FOREIGN KEY (id_servicio) REFERENCES servicios(id),
    FOREIGN KEY (id_horario) REFERENCES horarios(numero)
);
```

### Pruebas de Inserción

Puedes probar la inserción de datos en las tablas con los siguientes comandos SQL:

```sql
-- Insertar tipos de servicio
INSERT INTO servicios (nombre, precio) VALUES ('Consulta General', 50.00);
INSERT INTO servicios(nombre, precio) VALUES ('Cirugía Dental', 70.00);
INSERT INTO servicios (nombre, precio) VALUES ('Blanqueamiento', 40.00);

-- Insertar horarios
INSERT INTO horarios (horario) VALUES
('08:00'),
('09:00'),
('10:00'),
('11:00'),
('14:00'),
('15:00'),
('16:00');

-- Consultar datos
SELECT * FROM servicios;
SELECT * FROM pacientes;
SELECT * FROM horarios;
SELECT * FROM citas;
```

### Notas

- Asegúrate de tener Docker instalado y en funcionamiento en tu máquina.
- Puedes ajustar los valores predeterminados como el nombre de usuario, contraseña y nombre de la base de datos según tus necesidades.
- Los datos de prueba son solo ejemplos y deben adaptarse a los requerimientos específicos de tu aplicación.

¡Listo! Ahora tienes todo lo necesario para comenzar a trabajar con PostgreSQL en Docker. Si tienes alguna pregunta o necesitas ayuda adicional, no dudes en abrir un issue en el repositorio.